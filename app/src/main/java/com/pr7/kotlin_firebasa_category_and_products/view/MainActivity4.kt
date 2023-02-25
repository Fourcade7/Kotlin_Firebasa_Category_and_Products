package com.pr7.kotlin_firebasa_category_and_products.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.pr7.kotlin_firebasa_category_and_products.ProductModel
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.databinding.ActivityMain4Binding

class MainActivity4 : AppCompatActivity() {
    lateinit var binding: ActivityMain4Binding
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            var product:ProductModel= intent.getSerializableExtra("product") as ProductModel
            Glide.with(this@MainActivity4).load(product.imageurl).into(imageviewforview)
            textviewproductname.text="${product!!.name}"
            textviewproductdescriptonandprice.text="${product!!.price}\n${product!!.description}"
        }

    }
}