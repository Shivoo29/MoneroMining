package com.ivar7284.monerominingapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class profileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val logoutBtn: Button = findViewById(R.id.logoutBtn)
        val backBtn: ImageButton = findViewById(R.id.ib_backBtn)
        val pWallet: TextView = findViewById(R.id.tv_pWallet)

        backBtn.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java ))
            finish()
        }

        logoutBtn.setOnClickListener {
            //Toast.makeText(applicationContext,"not yet implemented",Toast.LENGTH_SHORT).show()
            //logout for now
            AuthUI.getInstance()
                .signOut(applicationContext)
                .addOnCompleteListener {
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
            }
        }

        pWallet.setOnClickListener {
            startActivity(Intent(applicationContext, walletScreen::class.java))
            finish()
        }

    }
}