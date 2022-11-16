package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity:AppCompatActivity() {

    private lateinit var btnlogin:Button
    private lateinit var btnsignup:Button
    private lateinit var edt_email:EditText
    private lateinit var edt_password:EditText

    private lateinit var mauth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        edt_email=findViewById(R.id.email1)
        edt_password=findViewById(R.id.pass1)
        btnlogin=findViewById(R.id.btn_login)
        btnsignup=findViewById(R.id.btn_signup)

        mauth= FirebaseAuth.getInstance()

        btnsignup.setOnClickListener{
            val intent= Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }

        btnlogin.setOnClickListener {
            val email=edt_email.text.toString()
            val pass=edt_password.text.toString()
            login(email,pass)
        }

    }
    private fun login(email:String,password:String)
    {
        mauth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   val intent=Intent(this@LoginActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "user not exist", Toast.LENGTH_SHORT).show()

                }
            }

    }
}