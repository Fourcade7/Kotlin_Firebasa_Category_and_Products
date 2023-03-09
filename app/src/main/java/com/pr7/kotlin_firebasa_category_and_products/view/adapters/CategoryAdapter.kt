package com.pr7.kotlin_firebasa_category_and_products.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pr7.kotlin_firebasa_category_and_products.CategoryModel
import com.pr7.kotlin_firebasa_category_and_products.ProductModel
import com.pr7.kotlin_firebasa_category_and_products.databinding.RecyclerviewItemCategoryBinding
import com.pr7.kotlin_firebasa_category_and_products.view.MainActivity
import com.pr7.kotlin_firebasa_category_and_products.view.MainActivity3

class CategoryAdapter constructor(
    val context: Context,
    var arraylist: ArrayList<CategoryModel>,
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    val mainActivity:MainActivity=context as MainActivity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view=RecyclerviewItemCategoryBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryViewHolder(view)
    }
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            textviewcategorytext.text=arraylist.get(position).name
            Glide.with(context).load(arraylist.get(position).imageurl).into(imageviewcategoryimage)


            linearlayoutcategorylayout.setOnLongClickListener {
                val intent=Intent(context,MainActivity3::class.java)
                intent.putExtra("name",arraylist.get(position).name)
                intent.putExtra("image",arraylist.get(position).imageurl)
                if (mainActivity.admin){
                    context.startActivity(intent)
                }

                return@setOnLongClickListener true
            }

            linearlayoutcategorylayout.setOnClickListener {
                mainActivity.categorychanged(arraylist.get(position).name!!)
            }
        }
    }

    override fun getItemCount(): Int =arraylist.size


    fun filterList(filteredList: ArrayList<CategoryModel>) {
        arraylist = filteredList
        notifyDataSetChanged()
    }

    class CategoryViewHolder(val binding: RecyclerviewItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}