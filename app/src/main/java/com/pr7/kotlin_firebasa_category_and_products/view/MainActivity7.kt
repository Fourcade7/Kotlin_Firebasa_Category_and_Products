package com.pr7.kotlin_firebasa_category_and_products.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.databinding.ActivityMain7Binding
import com.pr7.kotlin_firebasa_category_and_products.viewmodel.ProductViewModel
import java.util.Calendar

class MainActivity7 : AppCompatActivity() {
    lateinit var productViewModel: ProductViewModel
    lateinit var binding: ActivityMain7Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain7Binding.inflate(layoutInflater)
        setContentView(binding.root)
        title=intent.getStringExtra("username")
       var username=intent.getStringExtra("username")
       var surname=intent.getStringExtra("surname")
       var phone=intent.getStringExtra("phone")
       var adress=intent.getStringExtra("adress")
        productViewModel=ViewModelProvider(this@MainActivity7).get(ProductViewModel::class.java)

        productViewModel.readallorderss(username!!).observe(this@MainActivity7,{
                var totalcost:Int=0
                for (i in 0 until it.size){
                    binding.textvieworderreadac7.append("${it.get(i).name} ${it.get(i).price}\n")
                    totalcost=totalcost+it.get(i).price!!.toInt()
                }
                binding.textvieworderreadac7.append("\nTotal Cost $totalcost")
        })

        binding.buttonbuy.setOnClickListener {
            val calendar=Calendar.getInstance()
            var day=calendar.get(Calendar.DAY_OF_MONTH)
            var month=calendar.get(Calendar.MONTH)+1
            var year=calendar.get(Calendar.YEAR)
            var datatime="$day/$month/$year"
            productViewModel.buy(
                orders = binding.textvieworderreadac7.text.toString(),
                username = username,
                surname = surname!!,
                phone = phone!!,
                address = adress!!,
                datatime = datatime
            )
        }

        productViewModel.buysucces().observe(this@MainActivity7,{
            if (it){
                Toast.makeText(this@MainActivity7,"Zakaz Berildi",Toast.LENGTH_SHORT).show()
            }
        })



    }
}