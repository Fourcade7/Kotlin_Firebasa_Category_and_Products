package com.pr7.kotlin_firebasa_category_and_products.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr7.kotlin_firebasa_category_and_products.CategoryModel
import com.pr7.kotlin_firebasa_category_and_products.model.repositories.RepositoryCategory

class CategoryViewModel constructor(
    val repositoryCategory: RepositoryCategory= RepositoryCategory()
):ViewModel() {

    fun uploadcategorytofirebse(name:String,uri: Uri){
        repositoryCategory.uploadcategory(name,uri)
    }

    fun uploadcategoryprogress():MutableLiveData<Double>{
        return repositoryCategory.livedataprogres
    }
    fun uploadsucces():MutableLiveData<Boolean>{
        return repositoryCategory.livedatasucces
    }

    fun readallcategory():MutableLiveData<ArrayList<CategoryModel>>{
        return repositoryCategory.readalldata()
    }
}