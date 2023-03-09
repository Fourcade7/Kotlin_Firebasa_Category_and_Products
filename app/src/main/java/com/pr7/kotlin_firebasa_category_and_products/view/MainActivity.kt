package com.pr7.kotlin_firebasa_category_and_products.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.pr7.kotlin_firebasa_category_and_products.CategoryModel
import com.pr7.kotlin_firebasa_category_and_products.ProductModel
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.databinding.ActivityMainBinding
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.IMAGES
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.USERNAME
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.USER_INFORMATION
import com.pr7.kotlin_firebasa_category_and_products.view.adapters.AllProductsAdapter
import com.pr7.kotlin_firebasa_category_and_products.view.adapters.CategoryAdapter
import com.pr7.kotlin_firebasa_category_and_products.viewmodel.CategoryViewModel
import com.pr7.kotlin_firebasa_category_and_products.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CategoryViewModel
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var productViewModel: ProductViewModel
    lateinit var allProductsAdapter: AllProductsAdapter
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReferenceproductimages: DatabaseReference
    var databaseReferenceorder: DatabaseReference=FirebaseDatabase.getInstance().getReference().child(Constants.ORDERS)
    var arraylistallorder=ArrayList<ProductModel>()
    var useruid:String?=null
    var admin=false

    //Search
    var arraylistallproducts=ArrayList<ProductModel>()
    var arraylistallcategories=ArrayList<CategoryModel>()

    //
    var surname:String=""
    var phone:String=""
    var adress:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        viewModel= ViewModelProvider(this@MainActivity).get(CategoryViewModel::class.java)
        productViewModel= ViewModelProvider(this@MainActivity).get(ProductViewModel::class.java)
        useruid=intent.getStringExtra("uid")
        if (useruid=="XGPkArKyVOeqdjgtl9MFzLsz0D12"){
            admin=true
            binding.imageviewopenac2.visibility= View.VISIBLE
        }
        databaseReference=FirebaseDatabase.getInstance().getReference().child(USER_INFORMATION).child(useruid!!)
        databaseReferenceproductimages=FirebaseDatabase.getInstance().getReference().child(IMAGES)

        val view=binding.navigationview.getHeaderView(0)
        val textViewusername:TextView=view.findViewById(R.id.textviewheaderusername)
        val textViewemail:TextView=view.findViewById(R.id.textviewheaderemail)
        val textViewphone:TextView=view.findViewById(R.id.textviewheaderphone)

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var name=snapshot.child("name").getValue().toString()
                var surnamee=snapshot.child("surname").getValue().toString()
                var phonee=snapshot.child("phone").getValue().toString()
                var address=snapshot.child("address").getValue().toString()
                var email=snapshot.child("email").getValue().toString()
                var password=snapshot.child("password").getValue().toString()
                USERNAME=name
                surname=surnamee
                phone=phonee
                adress=address
                Log.d("PR77777", "onDataChange: $USERNAME")

                binding.textviewtitle.text=name
                textViewusername.text="$name $surname"
                textViewemail.text=email
                textViewphone.text=phone


                //ORDER BADGE
                databaseReferenceorder.child(name).addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        arraylistallorder.clear()
                        for (datasnapshot:DataSnapshot in snapshot.children){
                            var productModel=datasnapshot.getValue(ProductModel::class.java)
                            arraylistallorder.add(productModel!!)
                        }
                       binding.badgecounter.text="${arraylistallorder.size}"
                    }
                    override fun onCancelled(error: DatabaseError) {

                    }
                })
                //ORDER
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.apply {
            imageviewopenac2.setOnClickListener {
                startActivity(Intent(this@MainActivity,MainActivity2::class.java))
            }
            recyclerviewcategory.layoutManager=LinearLayoutManager(this@MainActivity,RecyclerView.HORIZONTAL,false)
            viewModel.readallcategory().observe(this@MainActivity,{
                arraylistallcategories=it
                categoryAdapter=CategoryAdapter(this@MainActivity,it)
                recyclerviewcategory.adapter=categoryAdapter
            })

            recyclerviewallproducts.layoutManager=GridLayoutManager(this@MainActivity,3)

            productViewModel.readallproducts().observe(this@MainActivity,{
                arraylistallproducts=it
                it.shuffle()
                allProductsAdapter=AllProductsAdapter(this@MainActivity,it)
                recyclerviewallproducts.adapter=allProductsAdapter
            })


            imageviewopennavigation.setOnClickListener {
                drawerlayout.openDrawer(Gravity.LEFT)
            }

            navigationview.setNavigationItemSelectedListener {

                when(it.itemId){
                    R.id.item1->{
                        val intent=Intent(this@MainActivity,MainActivity8::class.java)
                        intent.putExtra("phone", phone)
                        startActivity(intent)
                    }
                    R.id.item2->{
                        save(null)
                        finish()
                    }
                }

                return@setNavigationItemSelectedListener true

            }

            imagevieworder.setOnClickListener {
                if (useruid=="XGPkArKyVOeqdjgtl9MFzLsz0D12"){
                    val intent=Intent(this@MainActivity,MainActivity9::class.java)
                    startActivity(intent)
                }else{
                    val intent=Intent(this@MainActivity,MainActivity7::class.java)
                    intent.putExtra("username", USERNAME)
                    intent.putExtra("surname", surname)
                    intent.putExtra("adress", adress)
                    intent.putExtra("phone", phone)
                    startActivity(intent)
                }

            }

            edittextsearch.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    filter(p0.toString())
                }

            })


        }
    }


    fun categorychanged(categoryname:String){
        productViewModel.readeverycategory(categoryname).observe(this@MainActivity,{
            allProductsAdapter=AllProductsAdapter(this@MainActivity,it)
            binding.recyclerviewallproducts.adapter=allProductsAdapter
        })

    }

    fun save(text: String?) {
        val editor = getSharedPreferences("Pr", MODE_PRIVATE).edit() as SharedPreferences.Editor
        editor.putString("pr", text)
        editor.commit()
    }


    //Search

    fun filter(text:String){
        val searcharraylist=ArrayList<ProductModel>()

        //PRoducts
        for (item:ProductModel in arraylistallproducts){
            if (item.name?.toLowerCase()!!.contains(text.toLowerCase())){
                searcharraylist.add(item)
            }
        }
        allProductsAdapter.filterList(searcharraylist)

        //Categories
        val searcharraylist2=ArrayList<CategoryModel>()
        for (item:CategoryModel in arraylistallcategories){
            if (item.name?.toLowerCase()!!.contains(text.toLowerCase())){
                searcharraylist2.add(item)
            }
        }
        categoryAdapter.filterList(searcharraylist2)


    }






}