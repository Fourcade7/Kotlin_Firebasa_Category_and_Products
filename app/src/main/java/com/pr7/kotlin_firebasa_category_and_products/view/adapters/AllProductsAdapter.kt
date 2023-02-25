package com.pr7.kotlin_firebasa_category_and_products.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pr7.kotlin_firebasa_category_and_products.ProductModel
import com.pr7.kotlin_firebasa_category_and_products.databinding.RecyclerviewItemCategoryBinding
import com.pr7.kotlin_firebasa_category_and_products.databinding.RecyclerviewItemallproductsBinding
import com.pr7.kotlin_firebasa_category_and_products.view.MainActivity4

class AllProductsAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<ProductModel>
):RecyclerView.Adapter<AllProductsAdapter.AllProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductsViewHolder {
        val view=RecyclerviewItemallproductsBinding.inflate(LayoutInflater.from(context),parent,false)
        return AllProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllProductsViewHolder, position: Int) {
        holder.binding.apply {
            textviewallproducts.text=arrayList.get(position).name
            Glide.with(context).load(arrayList.get(position).imageurl).into(imageviewallproducts)
            linearlayoutproductlayout.setOnClickListener {
                val intent=Intent(context,MainActivity4::class.java)
                intent.putExtra("product",arrayList.get(position))
                context.startActivity(intent)

            }
        }
    }

    override fun getItemCount(): Int =arrayList.size

    class AllProductsViewHolder(val binding: RecyclerviewItemallproductsBinding):RecyclerView.ViewHolder(binding.root)
}