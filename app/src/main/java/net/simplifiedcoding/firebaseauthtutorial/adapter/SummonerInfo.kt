package net.simplifiedcoding.firebaseauthtutorial.adapter

data class SummonerInfo(
    val nickname: String,
    val level: Long,
    val typeB: String,
    val typeM: String,
    val typeJ: String,
    val typeT: String,
    val winrateB: Double,
    val winrateM: Double,
    val winrateJ: Double,
    val winrateT: Double,
    val KDA_B: Double,
    val KDA_M: Double,
    val KDA_J: Double,
    val KDA_T: Double,
    val KDA_Total: Double,
    val winrateTotal: Double
)