package net.simplifiedcoding.firebaseauthtutorial.utils

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.adapter.Message
import net.simplifiedcoding.firebaseauthtutorial.adapter.Room
import net.simplifiedcoding.firebaseauthtutorial.ui.fragments.HomeFragment
import java.text.SimpleDateFormat


val db = FirebaseFirestore.getInstance()

fun getRoomMessageRef(roomId : String): CollectionReference {
    return db.collection("rooms").document(roomId).collection("messages")
}

fun getRoomListRef(uid : String): CollectionReference {
    return db.collection("users").document(uid).collection("enteredRoom")
}

fun getRoomRef(roomId: String): DocumentReference {
    return db.collection("rooms").document(roomId)
}

fun getMatchingRoomListRef(summonerLane: Long, partnerLane: Long): Task<QuerySnapshot> {
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


fun addRoomToUser(uid : String, room : Room){

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

fun addNewRoom(uid : String, nickname: String, summonerLane : Long, partnerLane : Long): Task<DocumentReference> {
    var roomId : String
    val room = hashMapOf(
        "createUser" to uid,
        "createUserNickname" to nickname,
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

