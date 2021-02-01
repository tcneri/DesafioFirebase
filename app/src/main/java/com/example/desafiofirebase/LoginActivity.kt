package com.example.desafiofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.desafiofirebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var bind: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val user = FirebaseAuth.getInstance().currentUser

        bind.btnLogin.setOnClickListener {
            getDataFields()
        }

        bind.tvCAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    fun getDataFields() {
        var email = bind.etEmailL.text.toString()
        var password = bind.etPasswordL.text.toString()
        var emailNB = email.isNotBlank()
        var passwordnB = password.isNotBlank()


        if (emailNB && passwordnB) {

            sendDataFirebase(email, password)

        } else {
            showMsg("Preencha todas as informações")
        }

    }

    fun sendDataFirebase(email:String, password:String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result?.user!!
                    callHome()
                }else{
                    showMsg("Usuário não encontrado")
                }
            }
    }

    fun callHome(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    fun showMsg(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}