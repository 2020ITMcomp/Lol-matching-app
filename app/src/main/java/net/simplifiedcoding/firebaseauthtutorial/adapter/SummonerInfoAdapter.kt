package net.simplifiedcoding.firebaseauthtutorial.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.databinding.BindableItem
import kotlinx.android.synthetic.main.fragment_room_history.*
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentMatchHistoryBinding
import net.simplifiedcoding.firebaseauthtutorial.databinding.RoomListBinding
import java.text.SimpleDateFormat
import kotlin.math.roundToInt


fun summonerAdapt(binding: FragmentMatchHistoryBinding, summonerInfo: SummonerInfo) {
    binding.name

    binding.name.text = summonerInfo.nickname
    binding.level.text = summonerInfo.level.toString()
    binding.typeB.text = summonerInfo.typeB
    binding.typeM.text = summonerInfo.typeM
    binding.typeJ.text = summonerInfo.typeJ
    binding.typeT.text = summonerInfo.typeT

    val winrateB = (summonerInfo.winrateB * 1000).roundToInt() / 10f
    val winrateM = (summonerInfo.winrateM * 1000).roundToInt() / 10f
    val winrateJ = (summonerInfo.winrateJ * 1000).roundToInt() / 10f
    val winrateT = (summonerInfo.winrateT * 1000).roundToInt() / 10f
    val winrateTotal = (summonerInfo.winrateTotal * 1000).roundToInt() / 10f

    binding.winrateB.text = winrateB.toString() + "%"
    binding.winrateM.text = winrateM.toString() + "%"
    binding.winrateJ.text = winrateJ.toString() + "%"
    binding.winrateT.text = winrateT.toString() + "%"
    binding.winrateTotal.text = winrateTotal.toString() + "%"

    val KDAB = (summonerInfo.KDA_B * 100).roundToInt() / 100f
    val KDAM = (summonerInfo.KDA_M * 100).roundToInt() / 100f
    val KDAJ = (summonerInfo.KDA_J * 100).roundToInt() / 100f
    val KDAT = (summonerInfo.KDA_T * 100).roundToInt() / 100f
    val KDATotal = (summonerInfo.KDA_Total * 100).roundToInt() / 100f

    binding.KDAB.text = KDAB.toString()
    binding.KDAM.text = KDAM.toString()
    binding.KDAJ.text = KDAJ.toString()
    binding.KDAT.text = KDAT.toString()
    binding.KDATotal.text = KDATotal.toString()



}