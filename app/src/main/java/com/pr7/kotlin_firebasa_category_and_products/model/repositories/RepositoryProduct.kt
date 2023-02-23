package com.pr7.kotlin_firebasa_category_and_products.model.repositories

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.pr7.kotlin_firebasa_category_and_products.CategoryModel
import com.pr7.kotlin_firebasa_category_and_products.ProductModel
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.ALLPRODUCTS
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.CATEGORIES
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.PRODUCTS

class RepositoryProduct constructor(
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child(PRODUCTS),
    var databaseReferenceall: DatabaseReference = FirebaseDatabase.getInstance().getReference().child(ALLPRODUCTS),
    var storageReference: StorageReference = FirebaseStorage.getInstance().getReference().child(PRODUCTS)
) {

    fun addproduct(
        categoryname:String,
        name:String,
        uri:Uri,
        price:String,
        description:String
    ){

        if (uri != null) {
            val filereference: StorageReference = storageReference.child(System.currentTimeMillis().toString() + "." + System.currentTimeMillis().toString())
            filereference.putFile(uri)
                .addOnSuccessListener {
                    filereference.downloadUrl.addOnSuccessListener {
                        var pushkey = databaseReference.push().key.toString()
                        //Realtime Database
                        val product = ProductModel(name,it.toString(),price,description,pushkey)
                        databaseReference.child(categoryname).child(pushkey).setValue(product)
                        databaseReferenceall.child(pushkey).setValue(product)


                    }
                }
                .addOnProgressListener {
                    val progress: Double = 100.0 * it.getBytesTransferred() / it.getTotalByteCount()


                }
        }
    }

}