package com.ivar7284.monerominingapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val rBackBtn:ImageButton = findViewById(R.id.ib_backBtn_register)
        val registerBtn:Button = findViewById(R.id.registerBtn)
        val rName:EditText = findViewById(R.id.registerName)
        val rPhone:EditText = findViewById(R.id.registerPhone)
        val rMail:EditText = findViewById(R.id.registerUser)
        val rPass:EditText = findViewById(R.id.registerPass)
        val rConfirmPass:EditText = findViewById(R.id.registerConfirmPass)
        val loginBtn:TextView = findViewById(R.id.tv_login)

        rBackBtn.setOnClickListener {
            startActivity(Intent(applicationContext,LoginActivity::class.java))
            finish()
        }

        loginBtn.setOnClickListener {
            startActivity((Intent(applicationContext,LoginActivity::class.java)))
            finish()
        }

        registerBtn.setOnClickListener {
            //sends data to server
            //for now:
            //startActivity(Intent(applicationContext,LoginActivity::class.java))
            //finish()

            val email = rMail.text.toString()
            val phone = rPhone.text.toString()
            val fullname = rName.text.toString()
            val password = rPass.text.toString()
            val confirmPass = rConfirmPass.text.toString()

            if (email.isEmpty()||phone.isEmpty()||fullname.isEmpty()||password.isEmpty()||confirmPass.isEmpty()){
                Toast.makeText(applicationContext,"ALL FIELDS MUST BE FILLED", Toast.LENGTH_SHORT).show()
            }else if (password!=confirmPass){
                Toast.makeText(applicationContext,"CONFIRMED PASSWORD MUST BE SAME", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){ task ->
                        if(task.isSuccessful){
                            Toast.makeText(applicationContext,"Registration Successful",Toast.LENGTH_LONG).show()
                            startActivity(Intent(applicationContext,LoginActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(applicationContext,"Registration Failed: ${task.exception?.message}",Toast.LENGTH_LONG).show()
                        }
                    }
            }

        }

    }
}