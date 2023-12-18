package com.ivar7284.monerominingapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class Details : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        //start button activity
        val mWallet:EditText = findViewById(R.id.miningWallet)
        val mPool:EditText = findViewById(R.id.miningPool)
        val mUser:EditText = findViewById(R.id.miningUser)
        val mSubmit:Button = findViewById(R.id.miningSubmitBtn)
        val mBackBtn:ImageButton = findViewById(R.id.ib_backBtn_details)

        mBackBtn.setOnClickListener{
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }

        mSubmit.setOnClickListener {
            Toast.makeText(applicationContext,"not implemented yet", Toast.LENGTH_SHORT).show()
            //for now:
            Toast.makeText(applicationContext,"SUBMITTED", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }


    }
}