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
import com.example.desafiofirebase.databinding.ActivityRegisterBinding
import com.example.desafiofirebase.databinding.ActivityRegisterGameBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_register_game.*

class RegisterGameActivity : AppCompatActivity() {
    private lateinit var bind:ActivityRegisterGameBinding

    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    private val CODE_IMG = 1000


    private val viewModel by viewModels<RegisterGameViewModel>{
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return RegisterGameViewModel(cr) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterGameBinding.inflate(layoutInflater)
        setContentView(bind.root)
        config()




        bind.ivAddGameR.setOnClickListener {
            getRes()
        }
        bind.btnSaveGame.setOnClickListener {
            viewModel.imgGame.observe(this){
                viewModel.sendGame(Game(
                        bind.etNameGameR.text.toString(),
                        bind.etCreatedGR.text.toString(),
                        bind.etDescriptionGR.text.toString(),
                        it
                ))
            }

            startActivity(Intent(this, HomeActivity::class.java))


        }

    }



    fun config(){
        var number = (0..200).random()
        var nString = number.toString()
        alertDialog = SpotsDialog.Builder().setContext(this).build()

        storageReference = FirebaseStorage.getInstance().getReference(nString)
    }

    fun getRes(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Imagem Game."), CODE_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CODE_IMG){
            alertDialog.show()
            val uploadFile = data?.data?.let { storageReference.putFile(it) }
            if( uploadFile != null){
                val task = uploadFile.continueWithTask { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Imagem carrregada com sucesso!", Toast.LENGTH_SHORT).show()
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
                    }
                }
            }else{
                alertDialog.dismiss()
                Toast.makeText(this, "Nenhuma imagem foi carregada.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}