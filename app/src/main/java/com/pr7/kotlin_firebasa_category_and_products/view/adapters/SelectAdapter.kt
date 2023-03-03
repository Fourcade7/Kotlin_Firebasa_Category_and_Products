package com.pr7.kotlin_firebasa_category_and_products.view.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pr7.kotlin_firebasa_category_and_products.R

class SelectAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<Uri>,

):RecyclerView.Adapter<SelectAdapter.SelectViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.recyclerview_selectimages,parent,false)
        return SelectViewHolder(view)
    }
    override fun onBindViewHolder(holder: SelectViewHolder, position: Int) {
        holder.imageview.setImageURI(arrayList.get(position))
    }
    override fun getItemCount(): Int =arrayList.size

    class SelectViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val imageview:ImageView=itemview.findViewById(R.id.imageviewselectedproduct)
    }

}