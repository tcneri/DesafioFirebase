package com.example.desafiofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiofirebase.databinding.ActivityLoginBinding
import com.example.desafiofirebase.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var bind: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.btnRegister.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}