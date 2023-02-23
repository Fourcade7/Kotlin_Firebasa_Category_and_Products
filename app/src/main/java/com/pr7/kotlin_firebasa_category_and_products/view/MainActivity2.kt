package com.pr7.kotlin_firebasa_category_and_products.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.databinding.ActivityMain2Binding
import com.pr7.kotlin_firebasa_category_and_products.viewmodel.CategoryViewModel

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    var imageuri: Uri?=null
    lateinit var viewModel: CategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this@MainActivity2).get(CategoryViewModel::class.java)


        binding.apply {
            imageviewopengallery.setOnClickListener {
            openFileChooser()
            }
            buttonaddcategory.setOnClickListener {
                viewModel.uploadcategorytofirebse(edittexcategoryname.text.toString(),imageuri!!)
            }
            viewModel.uploadsucces().observe(this@MainActivity2,{
                if (it){
                    showprogressbar()

                }else{
                    hideprogressbar()
                }
            })
            viewModel.uploadcategoryprogress().observe(this@MainActivity2,{
                    progressBarhorizontal.progress=it.toInt()
                    textviewprogress.text="${it.toInt()} %"
            })

        }

    }


    //Open Gallery
    fun openFileChooser() {
        getContent.launch("image/*")
    }
    //Open Gallery and Set image to imageview
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        binding.imageviewopengallery.setImageURI(uri)
        if (uri != null) {
            imageuri = uri
            binding.buttonaddcategory.isEnabled=true
        }
    }


    //Show progressbar hide progressbar
    fun showprogressbar(){
        binding.progressBarhorizontal.visibility= View.VISIBLE
        binding.textviewprogress.visibility= View.VISIBLE
    }
    fun hideprogressbar(){
        binding.progressBarhorizontal.visibility= View.GONE
        binding.textviewprogress.visibility= View.GONE
    }
    //Show progressbar hide progressbar
}