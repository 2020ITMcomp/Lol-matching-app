package net.simplifiedcoding.firebaseauthtutorial.adapter

data class Match(
    val winlose : Boolean,
    val gameDuration : Long,
    val champion : Integer,
    val kill : Integer,
    val death : Integer,
    val assist : Integer,
//    val kda : Double,
    val level : Integer,
    val cs : Integer,
    val gold : Integer,
    val firstSpell : Integer,
    val secondSpell : Integer,
    val killSpring : Integer,
    val timeStamp : Long
)