package com.ivar7284.monerominingapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        //check for user is already signed in or not
        val currentUser: FirebaseUser? = auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val loginBtn:Button = findViewById(R.id.loginBtn)
        val password:EditText = findViewById(R.id.loginPass)
        val eMail:EditText = findViewById(R.id.loginUser)
        val register:TextView = findViewById(R.id.tv_register)

        loginBtn.setOnClickListener {
            //Toast.makeText(applicationContext,"not implemented yet", Toast.LENGTH_SHORT).show()
            //just for now:
            //startActivity(Intent(applicationContext,MainActivity::class.java))

            val email = eMail.text.toString()
            val password = password.text.toString()


            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(applicationContext,"PLEASE FILL ALL THE DETAILS", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(applicationContext,"Login successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext,MainActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(applicationContext,"Login unsuccessful: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }

        register.setOnClickListener {
            startActivity(Intent(applicationContext,Register::class.java))
            finish()
        }

    }
}