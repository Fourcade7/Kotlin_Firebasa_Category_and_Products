package com.pr7.kotlin_firebasa_category_and_products.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pr7.kotlin_firebasa_category_and_products.R
import com.pr7.kotlin_firebasa_category_and_products.databinding.ActivityMainActivity6RegisterBinding
import com.pr7.kotlin_firebasa_category_and_products.model.UserModel
import com.pr7.kotlin_firebasa_category_and_products.utils.Constants.USER_INFORMATION

class MainActivity6Register : AppCompatActivity() {
    lateinit var binding: ActivityMainActivity6RegisterBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainActivity6RegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().getReference().child(USER_INFORMATION)

        binding.apply {
           buttonreg.setOnClickListener {
               firebaseAuth.createUserWithEmailAndPassword(edittextreglogin.text.toString(),edittextregpassword.text.toString()).addOnCompleteListener{
                   if (it.isSuccessful){
                       var uid=firebaseAuth.currentUser!!.uid
                       val userModel=UserModel(
                           name = edittextregname.text.toString(),
                           surname = edittextregsurname.text.toString(),
                           phone = edittextregnumber.text.toString(),
                           address = edittextregadress.text.toString(),
                           email = edittextreglogin.text.toString(),
                           password = edittextregpassword.text.toString(),
                           uid = uid
                       )

                       databaseReference.child(uid).setValue(userModel)
                       Toast.makeText(this@MainActivity6Register,"Registered succesfully",Toast.LENGTH_SHORT).show()
                   }
               }
           }
        }
    }
}