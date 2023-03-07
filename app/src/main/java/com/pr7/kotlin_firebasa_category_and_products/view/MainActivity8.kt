package com.pr7.kotlin_firebasa_category_and_products.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.databinding.ActivityMain8Binding
import com.pr7.kotlin_firebasa_category_and_products.model.OrderModel
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.HISTORY
import com.pr7.kotlin_firebasa_category_and_products.view.adapters.OrderAdapter

class MainActivity8 : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    lateinit var binding: ActivityMain8Binding
    val arraylist=ArrayList<OrderModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain8Binding.inflate(layoutInflater)
        setContentView(binding.root)
        var phone=intent.getStringExtra("phone")
        title=phone
        databaseReference=FirebaseDatabase.getInstance().getReference().child(HISTORY).child(phone!!)

        databaseReference.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
            arraylist.clear()
            for (datasnapshot:DataSnapshot in snapshot.children){
                val orderModel=datasnapshot.getValue(OrderModel::class.java)
                arraylist.add(orderModel!!)
            }
                binding.recyclerviewhistory.layoutManager=LinearLayoutManager(this@MainActivity8)
                val orderAdapter=OrderAdapter(this@MainActivity8,arraylist)
                binding.recyclerviewhistory.adapter=orderAdapter

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}