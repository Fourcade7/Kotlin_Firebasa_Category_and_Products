package com.pr7.kotlin_firebasa_category_and_products.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.pr7.kotlin_firebasa_category_and_products.model.repositories.RepositoryProduct

class ProductViewModel constructor(
    val repositoryProduct: RepositoryProduct= RepositoryProduct()
):ViewModel() {

    fun addnewproduct(
        categoryname:String,
        name:String,
        uri: Uri,
        price:String,
        description:String
    ){
        repositoryProduct.addproduct(categoryname, name, uri, price, description)
    }
}