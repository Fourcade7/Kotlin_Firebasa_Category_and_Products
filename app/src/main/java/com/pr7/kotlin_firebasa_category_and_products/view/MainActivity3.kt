package com.pr7.kotlin_firebasa_category_and_products.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.databinding.ActivityMain3Binding
import com.pr7.kotlin_firebasa_category_and_products.view.adapters.SelectAdapter
import com.pr7.kotlin_firebasa_category_and_products.viewmodel.ProductViewModel

class MainActivity3 : AppCompatActivity() {
    lateinit var binding: ActivityMain3Binding
    var categoryname:String?=null
    var categoryimage:String?=null
    var imageuri:Uri?=null
    lateinit var productViewModel: ProductViewModel
    var arraylistimage = ArrayList<Uri>()
    lateinit var selectAdapter: SelectAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        productViewModel=ViewModelProvider(this@MainActivity3).get(ProductViewModel::class.java)
        categoryname=intent.getStringExtra("name")
        categoryimage=intent.getStringExtra("image")

        binding.apply {
            textviewcategorynametitle.text=categoryname
            Glide.with(this@MainActivity3).load(categoryimage).into(circleimageviewcategory)

            imageviewopengalleryproduct.setOnClickListener {
                openFileChooser()
            }

            buttonaddproduct.setOnClickListener {
                productViewModel.addnewproduct(
                    categoryname!!,
                    edittexnameproduct.text.toString(),
                    imageuri!!,
                    edittexpriceproduct.text.toString(),
                    edittexdescriptionproduct.text.toString(),
                    arraylistimage
                )
            }

            productViewModel.uploadsucces().observe(this@MainActivity3,{
                if (it){
                    showprogress()
                }else{
                    hideprogress()
                    //startActivity(Intent(this@MainActivity3,MainActivity::class.java))
                }
            })

            productViewModel.uploadproductprogress().observe(this@MainActivity3,{
                textviewprogressproduct.text="${it.toInt()}%"
                progressBarhorizontalproduct.progress=it.toInt()
            })



        }

    }


    //Open Gallery
    fun openFileChooser() {
        getContent.launch("image/*")
    }
    //Open Gallery and Set image to imageview
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        binding.imageviewopengalleryproduct.setImageURI(uri)
        if (uri != null) {
            imageuri = uri
            arraylistimage.add(uri)
            binding.buttonaddproduct.isEnabled=true
            binding.recyclerviewselectimages.layoutManager=LinearLayoutManager(this@MainActivity3,RecyclerView.HORIZONTAL,false)
            selectAdapter=SelectAdapter(this@MainActivity3,arraylistimage)
            binding.recyclerviewselectimages.adapter=selectAdapter
        }
    }


    //show progress hide progress
    fun showprogress(){
        binding.apply {
            progressBarhorizontalproduct.visibility= View.VISIBLE
            textviewprogressproduct.visibility= View.VISIBLE
        }
    }
    fun hideprogress(){
        binding.apply {
            progressBarhorizontalproduct.visibility= View.GONE
            textviewprogressproduct.visibility= View.GONE
        }
    }

}