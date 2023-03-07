package com.pr7.kotlin_firebasa_category_and_products.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.databinding.ActivityMain9Binding
import com.pr7.kotlin_firebasa_category_and_products.model.OrderModel
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.ADMINORDER
import com.pr7.kotlin_firebasa_category_and_products.view.adapters.OrderAdapter
import com.pr7.kotlin_firebasa_category_and_products.view.adapters.OrderAdapterAdmin

class MainActivity9 : AppCompatActivity() {
    lateinit var binding: ActivityMain9Binding
    val arrayList=ArrayList<OrderModel>()
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain9Binding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference=FirebaseDatabase.getInstance().getReference().child(ADMINORDER)

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (datasnapshot: DataSnapshot in snapshot.children){
                    val orderModel=datasnapshot.getValue(OrderModel::class.java)
                    arrayList.add(orderModel!!)
                }
                binding.recyclerviewadminorder.layoutManager= LinearLayoutManager(this@MainActivity9)
                val orderAdapter= OrderAdapterAdmin(this@MainActivity9,arrayList)
                binding.recyclerviewadminorder.adapter=orderAdapter

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })



    }
}