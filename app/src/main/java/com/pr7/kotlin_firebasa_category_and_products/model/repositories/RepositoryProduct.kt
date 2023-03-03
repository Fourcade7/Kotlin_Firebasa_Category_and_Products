package com.pr7.kotlin_firebasa_category_and_products.model.repositories

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.pr7.kotlin_firebasa_category_and_products.CategoryModel
import com.pr7.kotlin_firebasa_category_and_products.ProductModel
import com.pr7.kotlin_firebasa_category_and_products.model.ImageModel
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.ALLPRODUCTS
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.CATEGORIES
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.IMAGES
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.PRODUCTS

class RepositoryProduct constructor(
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        .child(PRODUCTS),
    var databaseReferenceimages: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        .child(IMAGES),
    var databaseReferenceall: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        .child(ALLPRODUCTS),
    var storageReference: StorageReference = FirebaseStorage.getInstance().getReference()
        .child(PRODUCTS)
) {

    var livedatasucces = MutableLiveData<Boolean>()
    var livedataprogress = MutableLiveData<Double>()

    var livedataallproducts = MutableLiveData<ArrayList<ProductModel>>()
    var arraylistallproducts = ArrayList<ProductModel>()
    fun addproduct(
        categoryname: String,
        name: String,
        uri: Uri,
        price: String,
        description: String,
        arraylistimage: ArrayList<Uri>
    ) {
        var pushkey = databaseReference.push().key.toString()
        //Realtime Database
        val product = ProductModel(
            name = name,
            price = price,
            description = description,
            pushkey = pushkey
        )
        databaseReference.child(categoryname).child(pushkey).setValue(product)
        databaseReferenceall.child(pushkey).setValue(product)

        for (imguri in arraylistimage) {
            if (imguri != null) {
                succes(true)
                val filereference: StorageReference = storageReference.child(
                    System.currentTimeMillis().toString() + "." + System.currentTimeMillis()
                        .toString()
                )
                filereference.putFile(imguri)
                    .addOnSuccessListener {
                        filereference.downloadUrl.addOnSuccessListener {downloaduri ->
                            val imageModel = ImageModel(imageurl = downloaduri.toString())
                            databaseReferenceimages.child(pushkey).push().setValue(imageModel)
                            succes(false)
                        }
                    }
                    .addOnProgressListener {
                        val progress: Double =
                            100.0 * it.getBytesTransferred() / it.getTotalByteCount()
                        livedataprogress.value = progress


                    }
            }
        }








    }


    //read all products from Firebase
    fun readallproductsfirebase(): MutableLiveData<ArrayList<ProductModel>> {
        databaseReferenceall.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arraylistallproducts.clear()
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val productModel = datasnapshot.getValue(ProductModel::class.java)
                    arraylistallproducts.add(productModel!!)
                }
                livedataallproducts.value = arraylistallproducts

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return livedataallproducts
    }


    fun readeverycategory(categoryname: String): MutableLiveData<ArrayList<ProductModel>> {
        databaseReference.child(categoryname).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arraylistallproducts.clear()
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val productModel = datasnapshot.getValue(ProductModel::class.java)
                    arraylistallproducts.add(productModel!!)
                }
                livedataallproducts.value = arraylistallproducts

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return livedataallproducts
    }


    fun succes(boolean: Boolean) {
        livedatasucces.value = boolean
    }

}