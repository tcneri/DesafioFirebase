package com.example.desafiofirebase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference

class RegisterGameViewModel(val cr: CollectionReference):ViewModel(){
    val resSend = MutableLiveData<Boolean>()


    fun sendGame(game:Game){
        cr.document().set(game).addOnSuccessListener {
            resSend.value = true
        }.addOnCanceledListener {
            resSend.value = false
        }
    }

    fun getGames(){
        cr.get().addOnSuccessListener { games->
            for(game in games){


        }
    }
}

}
