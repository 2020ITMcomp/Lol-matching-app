package net.simplifiedcoding.firebaseauthtutorial.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import net.simplifiedcoding.firebaseauthtutorial.adapter.Message


val db = FirebaseFirestore.getInstance()

fun getRoomMessageRef(roomId : String): CollectionReference {
    return db.collection("rooms").document(roomId).collection("messages")
}

fun getRoomListRef(uid : String): CollectionReference {
    return db.collection("users").document(uid).collection("enteredRoom")
}

fun snapshotToMessage(snapshot: QueryDocumentSnapshot) : Message{
    return Message(
        uid = snapshot.getString("uid")!!,
        text_message_body = snapshot.getString("text_message_body")!!,
        text_message_name = snapshot.getString("text_message_name")!!,
        timeStamp = snapshot.getLong("timeStamp")!!
    )
}
