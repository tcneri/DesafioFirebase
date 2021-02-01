package com.example.desafiofirebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference


class EditGameViewModel(val cr: CollectionReference) : ViewModel() {
    val resSend = MutableLiveData<Boolean>()
    var resGame= MutableLiveData<Game>()

    //    var listGame = MutableLiveData<ArrayList<Game>>()


    fun sendGame(game: Game) {

        cr.document().set(game).addOnSuccessListener {
            resSend.value = true
        }.addOnCanceledListener {
            resSend.value = false
        }
        Log.i("FIREBASE", resSend.value.toString())

    }

//    fun saveUrlImage(img: String?){
//        if (img!=null){
//            imgGame.value = img
//        }
//
//    }


    fun getGame(titleGame: String) {

        cr.whereEqualTo("title", titleGame).get().addOnSuccessListener{ document ->
            var item = document.toObjects(Game::class.java)
            resGame.value = item[0]
        }


    }



}
