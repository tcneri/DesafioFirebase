package com.example.desafiofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiofirebase.databinding.ActivityGameDetailsBinding
import com.example.desafiofirebase.databinding.ActivityLoginBinding

class GameDetailsActivity : AppCompatActivity() {
    private lateinit var bind: ActivityGameDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}