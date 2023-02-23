package com.pr7.kotlin_firebasa_category_and_products.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.databinding.ActivityMainBinding
import com.pr7.kotlin_firebasa_category_and_products.view.adapters.CategoryAdapter
import com.pr7.kotlin_firebasa_category_and_products.viewmodel.CategoryViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CategoryViewModel
    lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        viewModel= ViewModelProvider(this@MainActivity).get(CategoryViewModel::class.java)

        binding.apply {
            imageviewopenac2.setOnClickListener {
                startActivity(Intent(this@MainActivity,MainActivity2::class.java))
            }
            recyclerviewcategory.layoutManager=LinearLayoutManager(this@MainActivity,RecyclerView.HORIZONTAL,false)
            viewModel.readallcategory().observe(this@MainActivity,{
                categoryAdapter=CategoryAdapter(this@MainActivity,it)
                recyclerviewcategory.adapter=categoryAdapter
            })
        }
    }
}