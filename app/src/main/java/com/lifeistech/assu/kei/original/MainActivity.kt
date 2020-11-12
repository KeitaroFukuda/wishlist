package com.lifeistech.assu.kei.original

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listButton.setOnClickListener {
            val intent = Intent(application, ListActivity::class.java)
            startActivity(intent)
        }

        add_Button.setOnClickListener {
            val intent = Intent(application, Add::class.java)
            startActivity(intent)
        }
    }

}