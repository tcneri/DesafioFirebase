package com.example.desafiofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiofirebase.databinding.ActivityGameDetailsBinding
import com.example.desafiofirebase.databinding.ActivityLoginBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_details.*

class GameDetailsActivity : AppCompatActivity() {
    private lateinit var bind: ActivityGameDetailsBinding

    private val homeViewModel by viewModels<HomeViewModel>{
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(cr) as T
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(bind.root)

        var idGame = intent.getIntExtra("id", 0)
        var titleG = intent.getStringExtra("title")

        setSupportActionBar(bind.toolbar)
        bind.toolbar.title = ""
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        homeViewModel.resListGame.observe(this){
            var game = it[idGame]
            if (game.gameUrlImg != null){
                Picasso.get().load(game.gameUrlImg).fit().into(bind.ivGameDt)
            }
            bind.tvTitleGameD.text = game.title
            bind.tvYearGameD.text = "Lançamento: " + game.createdAt
            bind.tvDescGameD.text = game.description

        }

        homeViewModel.getGames()

        bind.fbEditGame.setOnClickListener {
            if (titleG != null) {
                callEditGame(titleG)
            }
        }

//        Toast.makeText(this, idGame.toString(), Toast.LENGTH_SHORT).show()
    }

    fun callEditGame(titleG:String){
        var intent = Intent(this, EditGameActivity::class.java)
        intent.putExtra("title", titleG)
        startActivity(intent)
    }
}

