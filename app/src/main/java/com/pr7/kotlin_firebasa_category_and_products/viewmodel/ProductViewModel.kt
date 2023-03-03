package com.pr7.kotlin_firebasa_category_and_products.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr7.kotlin_firebasa_category_and_products.ProductModel
import com.pr7.kotlin_firebasa_category_and_products.model.ImageModel
import com.pr7.kotlin_firebasa_category_and_products.model.repositories.RepositoryProduct

class ProductViewModel constructor(
    val repositoryProduct: RepositoryProduct= RepositoryProduct()
):ViewModel() {

    fun addnewproduct(
        categoryname:String,
        name:String,
        uri: Uri,
        price:String,
        description:String,
        arraylistimage:ArrayList<Uri>
    ){
        repositoryProduct.addproduct(categoryname, name, uri, price, description,arraylistimage)
    }


    fun readallproducts():MutableLiveData<ArrayList<ProductModel>>{
      return  repositoryProduct.readallproductsfirebase()
    }

    fun readeverycategory(categoryname: String):MutableLiveData<ArrayList<ProductModel>>{
        return repositoryProduct.readeverycategory(categoryname)
    }

    fun uploadproductprogress(): MutableLiveData<Double> {
        return repositoryProduct.livedataprogress
    }
    fun uploadsucces(): MutableLiveData<Boolean> {
        return repositoryProduct.livedatasucces
    }


    fun productallimages(pushkey:String):MutableLiveData<ArrayList<ImageModel>>{
        return repositoryProduct.productallimages(pushkey)
    }
}