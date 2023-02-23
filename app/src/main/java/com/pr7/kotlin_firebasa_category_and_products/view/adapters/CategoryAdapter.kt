package com.pr7.kotlin_firebasa_category_and_products.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pr7.kotlin_firebasa_category_and_products.CategoryModel
import com.pr7.kotlin_firebasa_category_and_products.databinding.RecyclerviewItemCategoryBinding

class CategoryAdapter constructor(
    val context: Context,
    val arraylist: ArrayList<CategoryModel>,
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view=RecyclerviewItemCategoryBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryViewHolder(view)
    }
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            textviewcategorytext.text=arraylist.get(position).name
            Glide.with(context).load(arraylist.get(position).imageurl).into(imageviewcategoryimage)
        }
    }

    override fun getItemCount(): Int =arraylist.size



    class CategoryViewHolder(val binding: RecyclerviewItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}