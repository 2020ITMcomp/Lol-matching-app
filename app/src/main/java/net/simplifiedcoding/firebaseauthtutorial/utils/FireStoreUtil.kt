package net.simplifiedcoding.firebaseauthtutorial.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import net.simplifiedcoding.firebaseauthtutorial.adapter.Message


val db = FirebaseFirestore.getInstance()

fun getRoomMessageRef(roomId : String): CollectionReference {
    return db.collection("rooms").document(roomId).collection("messages")
}
