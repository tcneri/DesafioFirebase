package com.example.desafiofirebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

val db: FirebaseFirestore = FirebaseFirestore.getInstance()
val cr:CollectionReference = db.collection("games")