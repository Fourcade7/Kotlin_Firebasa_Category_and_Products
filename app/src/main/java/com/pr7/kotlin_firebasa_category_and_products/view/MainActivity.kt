package com.pr7.kotlin_firebasa_category_and_products.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.databinding.ActivityMainBinding
import com.pr7.kotlin_firebasa_category_and_products.view.adapters.AllProductsAdapter
import com.pr7.kotlin_firebasa_category_and_products.view.adapters.CategoryAdapter
import com.pr7.kotlin_firebasa_category_and_products.viewmodel.CategoryViewModel
import com.pr7.kotlin_firebasa_category_and_products.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CategoryViewModel
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var productViewModel: ProductViewModel
    lateinit var allProductsAdapter: AllProductsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        viewModel= ViewModelProvider(this@MainActivity).get(CategoryViewModel::class.java)
        productViewModel= ViewModelProvider(this@MainActivity).get(ProductViewModel::class.java)



        binding.apply {
            imageviewopenac2.setOnClickListener {
                startActivity(Intent(this@MainActivity,MainActivity2::class.java))
            }
            recyclerviewcategory.layoutManager=LinearLayoutManager(this@MainActivity,RecyclerView.HORIZONTAL,false)
            viewModel.readallcategory().observe(this@MainActivity,{
                categoryAdapter=CategoryAdapter(this@MainActivity,it)
                recyclerviewcategory.adapter=categoryAdapter
            })

            recyclerviewallproducts.layoutManager=GridLayoutManager(this@MainActivity,3)

            productViewModel.readallproducts().observe(this@MainActivity,{
                it.shuffle()
                allProductsAdapter=AllProductsAdapter(this@MainActivity,it)
                recyclerviewallproducts.adapter=allProductsAdapter
            })
        }
    }


    fun categorychanged(categoryname:String){
        productViewModel.readeverycategory(categoryname).observe(this@MainActivity,{
            allProductsAdapter=AllProductsAdapter(this@MainActivity,it)
            binding.recyclerviewallproducts.adapter=allProductsAdapter
        })

    }



}