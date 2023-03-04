package com.pr7.kotlin_firebasa_category_and_products.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.*
import com.pr7.kotlin_firebasa_category_and_products.ProductModel
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.databinding.ActivityMain4Binding
import com.pr7.kotlin_firebasa_category_and_products.model.ImageModel
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.IMAGES
import com.pr7.kotlin_firebasa_category_and_products.viewmodel.ProductViewModel

class MainActivity4 : AppCompatActivity() {
    lateinit var binding: ActivityMain4Binding
    lateinit var databaseReference: DatabaseReference
    var arraylist=ArrayList<ImageModel>()
    val imageList = ArrayList<SlideModel>()
    lateinit var productViewModel: ProductViewModel
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            var product:ProductModel= intent.getSerializableExtra("product") as ProductModel
           //Glide.with(this@MainActivity4).load(product.uri).into(imageviewforview)
            var pushkey=product.pushkey
            databaseReference=FirebaseDatabase.getInstance().getReference().child(IMAGES).child(pushkey!!)
            textviewproductname.text="${product!!.name}"
            textviewproductdescriptonandprice.text="${product!!.price}\n${product!!.description}"

            databaseReference.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    arraylist.clear()
                    for (datasnapshot:DataSnapshot in snapshot.children){
                        val imageModel=datasnapshot.getValue(ImageModel::class.java)
                        arraylist.add(imageModel!!)
                    }

                    for (img in arraylist){
                        imageList.add(SlideModel(img.imageurl, ScaleTypes.CENTER_CROP))
                    }
                    imageslider.setImageList(imageList)

                    Toast.makeText(this@MainActivity4,"${arraylist.size}",Toast.LENGTH_SHORT).show()

                }
                override fun onCancelled(error: DatabaseError) {

                }
            })


            //ORDER
            productViewModel=ViewModelProvider(this@MainActivity4).get(ProductViewModel::class.java)
            buttonaddtobadge.setOnClickListener {
            productViewModel.addneworder(
                name = product.name!!,
                imguri = product.imguri!!,
                price = product.price!!,
                description = product.description!!,
                pushkey = product.pushkey!!
            )
            }

        }

    }
}