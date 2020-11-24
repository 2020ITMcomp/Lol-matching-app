package net.simplifiedcoding.firebaseauthtutorial.adapter

import com.xwray.groupie.Group

data class Match(
    val gameDuration : Long,
    val gameCreation : Long,
    val championId : Long,
    val spell1Id : Long,
    val spell2Id : Long,
    val win : Boolean,
    val champLevel : Long,
    val kills : Long,
    val death : Long,
    val assist : Long,
    val kda : Float,
    val cs : Long,
    val goldEarned : Long,
    val killSpring : Long
)