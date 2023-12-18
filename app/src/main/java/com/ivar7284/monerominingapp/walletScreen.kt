package com.ivar7284.monerominingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

class walletScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_screen)

        val homeBtn : LinearLayout = findViewById(R.id.homeBtn)
        val profileBtn: LinearLayout = findViewById(R.id.profileBtn)
        val moreTransactions: Button = findViewById(R.id.moreTransactionsBtn)

        //profile-wallet valle loop kr bare mai kuch karna hai
        //hash-rate ko bhi realtime karna hai
        //wallet screen recycler view

//        val commands = arrayOf(
//            "apt update && apt upgrade",
//            "pkg install git build-essential cmake -y",
//            "git clone https://github.com/xmrig/xmrig.git",
//            "apt upgrade",
//            "cd xmrig",
//            "mkdir build",
//            "cd build",
//            "cmake .. -DWITH_HWLOC=OFF",
//            "make -j10"
//        )

        //recyclerView mai karna ye sab
        //transaction list to be made in recyclerview

        profileBtn.setOnClickListener {
            startActivity(Intent(applicationContext, profileActivity::class.java ))

        }
        homeBtn.setOnClickListener {
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }

        moreTransactions.setOnClickListener {
            Toast.makeText(applicationContext,"not implemented yet", Toast.LENGTH_SHORT).show()
        }

    }
}