package net.simplifiedcoding.firebaseauthtutorial.adapter

data class Match(
    val champion : String,
    val gameId : Long,
    val lane : String,
    val platformId : String,
    val queue : Integer,
    val role : String,
    val season : Integer,
    val timeStamp : Long
)