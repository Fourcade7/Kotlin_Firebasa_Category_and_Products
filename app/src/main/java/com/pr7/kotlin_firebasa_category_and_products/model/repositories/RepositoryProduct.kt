package com.pr7.kotlin_firebasa_category_and_products.model.repositories

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.pr7.kotlin_firebasa_category_and_products.CategoryModel
import com.pr7.kotlin_firebasa_category_and_products.ProductModel
import com.pr7.kotlin_firebasa_category_and_products.model.ImageModel
import com.pr7.kotlin_firebasa_category_and_products.model.OrderModel
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.ADMINORDER
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.ALLPRODUCTS
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.CATEGORIES
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.HISTORY
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.IMAGES
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.ORDERS
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.PRODUCTS
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.USERNAME

class RepositoryProduct constructor(
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        .child(PRODUCTS),
    var databaseReferenceimages: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        .child(IMAGES),
    var databaseReferenceall: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        .child(ALLPRODUCTS),
    var storageReference: StorageReference = FirebaseStorage.getInstance().getReference()
        .child(PRODUCTS),
    var databaseReferenceorder: DatabaseReference=FirebaseDatabase.getInstance().getReference().child(
        ORDERS),
    var databaseReferenceadminorder: DatabaseReference=FirebaseDatabase.getInstance().getReference().child(
        ADMINORDER),
    var databaseReferencehistory: DatabaseReference=FirebaseDatabase.getInstance().getReference().child(
        HISTORY)
) {

    var livedatasucces = MutableLiveData<Boolean>()
    var livedataprogress = MutableLiveData<Double>()

    var livedataallproducts = MutableLiveData<ArrayList<ProductModel>>()
    var arraylistallproducts = ArrayList<ProductModel>()

    //productallimages
    var livedataproductallimages=MutableLiveData<ArrayList<ImageModel>>()
    var arrayListproductallimages=ArrayList<ImageModel>()

    //ORDER
    var arraylistallorder=ArrayList<ProductModel>()
    var livedataallorder=MutableLiveData<ArrayList<ProductModel>>()
    //BUY
    var livedatabuy=MutableLiveData<Boolean>()
    fun addproduct(
        categoryname: String,
        name: String,
        uri: Uri,
        price: String,
        description: String,
        arraylistimage: ArrayList<Uri>
    ) {
        //realtime added
        var pushkey = databaseReference.push().key.toString()
        //Realtime Database
        val product = ProductModel(
            name = name,
            imguri=uri.toString(),
            price = price,
            description = description,
            pushkey = pushkey
        )
        databaseReference.child(categoryname).child(pushkey).setValue(product)

        if (uri != null) {
            succes(true)
            val filereference: StorageReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + System.currentTimeMillis()
                    .toString()
            )
            filereference.putFile(uri)
                .addOnSuccessListener {
                    filereference.downloadUrl.addOnSuccessListener {rasm->
                        databaseReferenceall.child(pushkey).setValue(product)
                    }
                }
                .addOnProgressListener {
                    val progress: Double =
                        100.0 * it.getBytesTransferred() / it.getTotalByteCount()
                    livedataprogress.value = progress


                }
        }
        //realtime added
        //add images
        for (imguri in arraylistimage) {
            if (imguri != null) {
                succes(true)
                val filereference: StorageReference = storageReference.child(
                    System.currentTimeMillis().toString() + "." + System.currentTimeMillis()
                        .toString()
                )
                filereference.putFile(imguri)
                    .addOnSuccessListener {
                        filereference.downloadUrl.addOnSuccessListener {rasm->
                            val imageModel = ImageModel(imageurl = rasm.toString())
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



        //add images




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


    fun productallimages(pushkey:String):MutableLiveData<ArrayList<ImageModel>>{
        databaseReferenceimages.child(pushkey).addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                arrayListproductallimages.clear()

                for (datasnapshot:DataSnapshot in snapshot.children){
                    val imageModel=datasnapshot.getValue(ImageModel::class.java)
                    arrayListproductallimages.add(imageModel!!)
                }
                livedataproductallimages.value=arrayListproductallimages


            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

        return livedataproductallimages

    }


    fun succes(boolean: Boolean) {
        livedatasucces.value = boolean
    }


    //Order

    fun neworder(
        name: String,
        imguri:String,
        price:String,
        description:String,
        pushkey: String,
    ){
        val productModel=ProductModel(
            name = name,
            imguri = imguri,
            price = price,
            description = description,
            pushkey = pushkey
        )

        databaseReferenceorder.child(USERNAME).child(pushkey).setValue(productModel)
    }


    //READ ORDER
    fun readallorders(username:String):MutableLiveData<ArrayList<ProductModel>>{
        databaseReferenceorder.child(username).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arraylistallorder.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    var productModel=datasnapshot.getValue(ProductModel::class.java)
                    arraylistallorder.add(productModel!!)
                }
                livedataallorder.value=arraylistallorder
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        return livedataallorder
    }


    //Buy
    fun buyproduct(
        orders :String,
        username :String,
        surname :String,
        phone :String,
        address :String,
        datatime :String
    ){
        livedatabuy.value=false

        val orderModel=OrderModel(
            orders = orders,
            username = username,
            surname = surname,
            phone = phone,
            address = address,
            datatime = datatime
        )
        databaseReferenceadminorder.push().setValue(orderModel)
        databaseReferencehistory.child(phone).push().setValue(orderModel).addOnCompleteListener {
            if (it.isSuccessful){
                livedatabuy.value=true
                databaseReferenceorder.child(username).removeValue()
            }
        }

    }
    //Buy


}