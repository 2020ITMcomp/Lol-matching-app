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

    binding.name.text = summonerInfo.nickname
    binding.level.text = summonerInfo.level.toString()
    binding.typeB.text = summonerInfo.typeB
    binding.typeM.text = summonerInfo.typeM
    binding.typeJ.text = summonerInfo.typeJ
    binding.typeT.text = summonerInfo.typeT
    binding.typeS.text = summonerInfo.typeS

    binding.winrateB.text = summonerInfo.winrateB.times(100).toString() + "%"
    binding.winrateM.text = summonerInfo.winrateB.times(100).toString() + "%"
    binding.winrateJ.text = summonerInfo.winrateB.times(100).toString() + "%"
    binding.winrateT.text = summonerInfo.winrateB.times(100).toString() + "%"
    binding.winrateS.text = summonerInfo.winrateB.times(100).toString() + "%"
    binding.winrateTotal.text = summonerInfo.winrateB.times(100).toString() + "%"

    binding.KDAB.text = summonerInfo.KDA_B.toString()
    binding.KDAM.text = summonerInfo.KDA_M.toString()
    binding.KDAJ.text = summonerInfo.KDA_J.toString()
    binding.KDAT.text = summonerInfo.KDA_T.toString()
    binding.KDAS.text = summonerInfo.KDA_S.toString()
    binding.KDATotal.text = summonerInfo.KDA_Total.toString()



}