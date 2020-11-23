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


fun summonerAdapt(binding: FragmentMatchHistoryBinding, summonerInfo: SummonerInfo) {
    binding.name

    binding.name.text = summonerInfo.nickname
    binding.level.text = summonerInfo.level
    binding.typeB.text = summonerInfo.typeB
    binding.typeM.text = summonerInfo.typeM
    binding.typeJ.text = summonerInfo.typeJ
    binding.typeT.text = summonerInfo.typeT
    binding.winrateB.text = summonerInfo.winrateB
    binding.winrateM.text = summonerInfo.winrateM
    binding.winrateJ.text = summonerInfo.winrateJ
    binding.winrateT.text = summonerInfo.winrateT
    binding.KDAB.text = summonerInfo.KDA_B
    binding.KDAM.text = summonerInfo.KDA_M
    binding.KDAJ.text = summonerInfo.KDA_J
    binding.KDAT.text = summonerInfo.KDA_T
    binding.KDATotal.text = summonerInfo.KDA_Total
    binding.winrateTotal.text = summonerInfo.winrateTotal
}