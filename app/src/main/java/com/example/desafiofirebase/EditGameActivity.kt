package com.example.desafiofirebase

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiofirebase.databinding.ActivityEditGameBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditGameActivity : AppCompatActivity() {
    private lateinit var bind: ActivityEditGameBinding

    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    private val CODE_IMG = 1000

    private val viewModel by viewModels<EditGameViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return EditGameViewModel(cr) as T
            }
        }
    }

    private val homeViewModel by viewModels<HomeViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(cr) as T
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityEditGameBinding.inflate(layoutInflater)
        setContentView(bind.root)
        config()


        var titleG = intent.getStringExtra("title")!!
        var createdG = intent.getStringExtra("created")!!

        viewModel.getGame(titleG, createdG)

        viewModel.resGame.observe(this) {
            if (it.gameUrlImg != null) {
                Picasso.get().load(it.gameUrlImg).fit().into(bind.ivAddGameE)
            }
            bind.etNameGameE.setText(it.title)
            bind.etCreatedGE.setText(it.createdAt)
            bind.etDescriptionGE.setText(it.description)

        }

        bind.ivAddGameE.setOnClickListener {
            getRes()
        }

        bind.btnSaveGame.setOnClickListener {



            var game = Game(
                    bind.etNameGameE.text.toString(),
                    bind.etCreatedGE.text.toString(),
                    bind.etDescriptionGE.text.toString(),
                    viewModel.imgGame.value)

            Log.i("TAG", game.toString())

            viewModel.editGame(titleG, createdG, game)

            homeViewModel.getGames()
            CoroutineScope(Dispatchers.Main).launch {
                delay(3)
                callHome()
            }

            finish()
        }
    }

    fun callHome() {
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }


    fun config() {
        var number = (0..200).random()
        var nString = number.toString()
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        storageReference = FirebaseStorage.getInstance().getReference(nString)
    }

    fun getRes() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Imagem Game."), CODE_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG) {
            alertDialog.show()
            val uploadFile = data?.data?.let { storageReference.putFile(it) }
            if( uploadFile != null){
                val task = uploadFile.continueWithTask { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Nova imagem carrregada com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                    storageReference!!.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        Log.i("URI", downloadUri.toString())
                        val url = downloadUri!!.toString().substring(0, downloadUri.toString().indexOf("&token"))
                        Log.i("URL da Imagem", url)
                        alertDialog.dismiss()
                        viewModel.saveUrlImage(downloadUri.toString())
                        viewModel.imgGame.observe(this){
                            Picasso.get().load(it).fit().into(bind.ivAddGameE)
                        }
                    }
                }
            }else{
                alertDialog.dismiss()
                Toast.makeText(this, "Nenhuma imagem foi carregada.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}