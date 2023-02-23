package com.pr7.kotlin_firebasa_category_and_products.model.repositories

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.pr7.kotlin_firebasa_category_and_products.CategoryModel
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.CATEGORIES

class RepositoryCategory constructor(
    var databaseReference: DatabaseReference=FirebaseDatabase.getInstance().getReference().child(CATEGORIES),
    val storageReference: StorageReference=FirebaseStorage.getInstance().getReference().child(CATEGORIES)
) {

    var livedatasucces=MutableLiveData<Boolean>()
    var livedataprogres=MutableLiveData<Double>()

    val arraylist=ArrayList<CategoryModel>()
    val livedataallcategories=MutableLiveData<ArrayList<CategoryModel>>()

    fun uploadcategory(name:String,uri: Uri){
        if (uri != null) {
            succes(true)
            val filereference: StorageReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + System.currentTimeMillis().toString()
            )
            filereference.putFile(uri)
                .addOnSuccessListener {
                    filereference.downloadUrl.addOnSuccessListener {
                        var pushkey = databaseReference.push().key.toString()
                        val category = CategoryModel(name, it.toString(), pushkey)
                        //Realtime Database
                        databaseReference.child(pushkey).setValue(category).addOnCompleteListener {
                            if (it.isSuccessful) {
                                   succes(false)
                            }
                        }

                    }
                }
                .addOnProgressListener {
                    val progress: Double = 100.0 * it.getBytesTransferred() / it.getTotalByteCount()
                    livedataprogres.value=progress

                }
        }
    }

    fun readalldata():MutableLiveData<ArrayList<CategoryModel>>{
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arraylist.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    val category=datasnapshot.getValue(CategoryModel::class.java)
                    arraylist.add(category!!)
                }

                livedataallcategories.value=arraylist
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return livedataallcategories
    }

    fun succes(boolean: Boolean){
        livedatasucces.value=boolean
    }
}