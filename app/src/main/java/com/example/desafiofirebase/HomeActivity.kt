package com.example.desafiofirebase

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import com.example.desafiofirebase.databinding.ActivityHomeBinding
import com.example.desafiofirebase.databinding.ActivityLoginBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var bind: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.fbAddGame.setOnClickListener {
            startActivity(Intent(this, RegisterGameActivity::class.java))
        }
    }


}