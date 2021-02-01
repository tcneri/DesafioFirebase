package com.example.desafiofirebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.launch

class RegisterGameViewModel(val cr: CollectionReference) : ViewModel() {
    val resSend = MutableLiveData<Boolean>()
    var imgGame = MutableLiveData<String?>()

    //    var listGame = MutableLiveData<ArrayList<Game>>()


    fun sendGame(game: Game) {

        cr.document().set(game).addOnSuccessListener {
            resSend.value = true
        }.addOnCanceledListener {
            resSend.value = false
        }
        Log.i("FIREBASE", resSend.value.toString())

    }

    fun saveUrlImage(img: String?){
        imgGame.value = img

    }


}
