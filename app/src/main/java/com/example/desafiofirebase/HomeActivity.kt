package com.example.desafiofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.GridLayout
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafiofirebase.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), GameAdapter.OnGameClickListener{
    private lateinit var bind: ActivityHomeBinding
    lateinit var adapter: GameAdapter

    private val viewModel by viewModels<HomeViewModel>{
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(cr) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.fbAddGame.setOnClickListener {
            startActivity(Intent(this, RegisterGameActivity::class.java))
        }

        viewModel.resListGame.observe(this){
            Log.i("LISTA", it[2].id.toString())
            adapter = GameAdapter(it, this)
            rvGames.adapter = adapter
            rvGames.layoutManager = GridLayoutManager(this, 2)

        }
        viewModel.getGames()

    }

    fun callDetails(id:Int, titleG:String){
        var intent = Intent(this, GameDetailsActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("title", titleG)
        startActivity(intent)
    }

    override fun gameClick(position: Int, id:Int, titleG:String) {
        callDetails(id, titleG)
    }


}