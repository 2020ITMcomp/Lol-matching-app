package net.simplifiedcoding.firebaseauthtutorial.utils

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.adapter.Message
import net.simplifiedcoding.firebaseauthtutorial.ui.fragments.HomeFragment


val db = FirebaseFirestore.getInstance()

fun getRoomMessageRef(roomId : String): CollectionReference {
    return db.collection("rooms").document(roomId).collection("messages")
}

fun getRoomListRef(uid : String): CollectionReference {
    return db.collection("users").document(uid).collection("enteredRoom")
}

fun getMatchingRoomListRef(summonerLane: Int, partnerLane: Int): Task<QuerySnapshot> {
    return db.collection("rooms")
        .whereEqualTo("summonerLane", summonerLane)
        .whereEqualTo("partnerLane", partnerLane).get()
}

fun getUserNicknameRef(uid : String) : Task<DocumentSnapshot> {
    return db.collection("users").document(uid).get()
}

fun getSummonerInfoRef(nickname : String): DocumentReference {
    return db.collection("summoner").document(nickname)
}

fun getSummonerGamedataRef(nickname : String): CollectionReference {
    return db.collection("game").document(nickname).collection("gamedata")
}

//-------------------------------------------------------------------------------------------------------------------


fun addRoomToUser(uid : String, roomId : String){

    val room = hashMapOf( // 추가적으로 데이터가 필요한지는 생각해봐야 할 듯.
        "roomId" to roomId
    )

    val userRef = db.collection("users").document(uid)
        .collection("enteredRoom").add(room)
        .addOnSuccessListener { it ->
            Log.d("referenceToUser", "Successfully adding a room to the user : ${it.id}")
        }
        .addOnFailureListener { e ->
            Log.w("referenceToUser", "Can not adding a room to the user!", e)
        }

}

fun snapshotToMessage(snapshot: QueryDocumentSnapshot) : Message{
    return Message(
        uid = snapshot.getString("uid")!!,
        text_message_body = snapshot.getString("text_message_body")!!,
        text_message_name = snapshot.getString("text_message_name")!!,
        timeStamp = snapshot.getLong("timeStamp")!!
    )
}

fun addNewRoom(uid : String, summonerLane : Int, partnerLane : Int): Task<DocumentReference> {
    var roomId : String
    val room = hashMapOf(
        "createUser" to uid,
        "summonerLane" to summonerLane,
        "partnerLane" to partnerLane,
        "timeStamp" to System.currentTimeMillis().toString(),
        "isClosed" to false
    )

    return db.collection("rooms").add(room)
}

fun userRenewalHistory(uid : String){
    val nicknameRef = getUserNicknameRef(uid)
    nicknameRef.addOnSuccessListener {
        val update = hashMapOf(
            "nickname" to it.data!!.get("nickname").toString()
        )

        db.collection("update").document(uid).set(update)
    }

}

