package com.example.desafiofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiofirebase.databinding.ActivityEditGameBinding
import com.example.desafiofirebase.databinding.ActivityRegisterGameBinding

class EditGameActivity : AppCompatActivity() {
    private lateinit var bind: ActivityEditGameBinding

    private val viewModel by viewModels<EditGameViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return EditGameViewModel(cr) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityEditGameBinding.inflate(layoutInflater)
        setContentView(bind.root)

        var titleG = intent.getStringExtra("title")!!

        viewModel.getGame(titleG)

        viewModel.resGame.observe(this){
            
        }


    }
}