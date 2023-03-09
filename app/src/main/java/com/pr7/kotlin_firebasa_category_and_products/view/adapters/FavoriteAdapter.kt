package com.pr7.kotlin_firebasa_category_and_products.view.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pr7.kotlin_firebasa_category_and_products.ProductModel
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.model.OrderModel
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.ORDERS
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.USERNAME

class FavoriteAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<ProductModel>
):RecyclerView.Adapter<FavoriteAdapter.FavorieteViewHolder>() {

    val databaseReference:DatabaseReference=FirebaseDatabase.getInstance().getReference().child(ORDERS).child(Constants.USERNAME)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavorieteViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.recyclerview_item_favorite,parent,false)
        return FavorieteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavorieteViewHolder, position: Int) {
        holder.textView.text="${arrayList.get(position).name}\n${arrayList.get(position).price} UZS"
        holder.imageview.setOnClickListener {
            databaseReference.child(arrayList.get(position).pushkey.toString()).removeValue()
            Log.d("PR77777", "onBindViewHolder: $USERNAME")
        }

    }

    override fun getItemCount(): Int=arrayList.size

    class FavorieteViewHolder(val itemview:View):RecyclerView.ViewHolder(itemview){
        val textView:TextView=itemview.findViewById(R.id.textviewfavoriteorder)
        val imageview:ImageView=itemview.findViewById(R.id.imageviewfavoritedelete)
    }
}