package com.pr7.kotlin_firebasa_category_and_products.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.model.OrderModel

class OrderAdapterAdmin constructor(
    val context: Context,
    val arrayList: ArrayList<OrderModel>
):RecyclerView.Adapter<OrderAdapterAdmin.OrderViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.recyclerview_item_order,parent,false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.textView.text="${arrayList.get(position).datatime}\n${arrayList.get(position).orders}\n${arrayList.get(position).username} ${arrayList.get(position).surname} ${arrayList.get(position).phone}\n${arrayList.get(position).address}"
    }

    override fun getItemCount(): Int=arrayList.size

    class OrderViewHolder(val itemview:View):RecyclerView.ViewHolder(itemview){
        val textView:TextView=itemview.findViewById(R.id.textvieworderread)
    }
}