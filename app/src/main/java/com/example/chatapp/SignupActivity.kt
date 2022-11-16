package com.example.chatapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity:AppCompatActivity() {

    private lateinit var edt_name: EditText
    private lateinit var btnsignup: Button
    private lateinit var edt_email: EditText
    private lateinit var edt_password: EditText

    private lateinit var mauth: FirebaseAuth
    private lateinit var mrf:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        edt_email=findViewById(R.id.email1)
        edt_password=findViewById(R.id.pass1)
        edt_name=findViewById(R.id.name1)
        btnsignup=findViewById(R.id.btn_signup)

        mauth= FirebaseAuth.getInstance()

        btnsignup.setOnClickListener {
            val email=edt_email.text.toString()
            val pass=edt_password.text.toString()
            val name=edt_name.text.toString()
            signup(name,email,pass)
        }

    }

    private fun signup(name:String,email:String,password:String){

        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    adduserdb(name,email,mauth.currentUser?.uid!!)
                   val intent=Intent(this@SignupActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                }
                else {
                   Toast.makeText(this,"Error message",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun adduserdb(name: String,email: String,uid:String){
            mrf=FirebaseDatabase.getInstance().getReference()

        mrf.child("user").child(uid).setValue(User(name, email, uid))

    }

}