package net.simplifiedcoding.firebaseauthtutorial.adapter

data class Room(
    val roomId : String,
    val timeStamp: String,
    val summonerLane : Long,
    val partnerLane : Long
)