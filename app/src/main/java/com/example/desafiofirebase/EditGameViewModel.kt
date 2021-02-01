package com.example.desafiofirebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject


class EditGameViewModel(val cr: CollectionReference) : ViewModel() {
    var imgGame = MutableLiveData<String?>()
    var resGame = MutableLiveData<Game>()
    var resListGame = MutableLiveData<ArrayList<Game>>()
//    var cgImg = Mutabliv

    //    var listGame = MutableLiveData<ArrayList<Game>>()


    fun editGame(titleG: String, createdG: String, gameEdit: Game) {

        cr.get().addOnSuccessListener { documents ->
            for (document in documents){
                if (createdG == document.getField("createdAt") && titleG ==document.getField("title")) {
                    var getP = document.reference.path
                    var splitItem = getP.split("/")
                    var itemPath = "/"+ splitItem[1]

                    cr.document(itemPath).update(
                            "title", gameEdit.title,
                            "createdAt",gameEdit.createdAt,
                            "description",gameEdit.description
                            )
                    if (gameEdit.gameUrlImg != null){
                        cr.document(itemPath).update("gameUrlImg", gameEdit.gameUrlImg)
                    }

                }
            }
        }
    }

    fun saveUrlImage(img: String?) {
        if (img != null) {
            imgGame.value = img
        }

    }




    fun getGame(titleGame: String, createdG: String) {

        cr.get().addOnSuccessListener { documents ->
            for (document in documents){
                if (createdG == document.getField("createdAt") && titleGame == document.getField("title")){
                    var getP = document.reference.path
                    var splitItem = getP.split("/")
                    var itemPath = "/"+ splitItem[1]
                    var doc = cr.document(itemPath).get().addOnSuccessListener {
                        resGame.value = document.toObject(Game::class.java)
                    }
            }

        }
//        cr.whereEqualTo("title", titleGame).get().addOnSuccessListener { document ->
//            document.documents.forEach{
//                if (createdG == it.getField("createdAt")){
//                    var itemPath = it.reference.path
//                    var doc = cr.document(itemPath).get().addOnSuccessListener {
//                        resGame.value = it.toObject(Game::class.java)
//                    }
//
//                }
//            }

        }


    }



}
