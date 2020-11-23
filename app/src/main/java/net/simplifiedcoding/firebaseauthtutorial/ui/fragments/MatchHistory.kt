package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import net.simplifiedcoding.firebaseauthtutorial.adapter.SummonerInfo
import net.simplifiedcoding.firebaseauthtutorial.adapter.summonerAdapt
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentMatchHistoryBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.getSummonerInfoRef
import net.simplifiedcoding.firebaseauthtutorial.utils.getUserNicknameRef
import net.simplifiedcoding.firebaseauthtutorial.utils.userRenewalHistory


class MatchHistory : Fragment() {

    private lateinit var binding : FragmentMatchHistoryBinding
    private lateinit var nickname : String
    private lateinit var uid : String
    private lateinit var recyclerView : RecyclerView
    private val matchAdapter = GroupAdapter<GroupieViewHolder>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMatchHistoryBinding.inflate(inflater, container, false)
        uid = FirebaseAuth.getInstance().uid.toString()
        nickname = arguments!!.get("nickname").toString()

        InputUserAbstract()

        recyclerView = binding.historyView.apply {
            setHasFixedSize(true)
            adapter = matchAdapter
        }
        // 유저 닉네임을 기준으로 최

        binding.renewalButton.setOnClickListener {


            userRenewalHistory(uid)

        }



        return binding.root
    }

    private fun InputUserAbstract() {
        binding.apply {

            getSummonerInfoRef(nickname).get().addOnSuccessListener { info ->
                val summonerInfo = SummonerInfo(
                    nickname = info.get("name").toString(),
                    level = info.get("summonerLevel").toString(),
                    typeB = info.get("B_Feature").toString(),
                    typeM = info.get("M_Feature").toString(),
                    typeJ = info.get("J_Feature").toString(),
                    typeT = info.get("T_Feature").toString(),
                    winrateB = info.get("B_Win").toString(),
                    winrateM = info.get("M_Win").toString(),
                    winrateJ = info.get("J_Win").toString(),
                    winrateT = info.get("T_Win").toString(),
                    KDA_B = info.get("B_KDA").toString(),
                    KDA_M = info.get("M_KDA").toString(),
                    KDA_J = info.get("J_KDA").toString(),
                    KDA_T = info.get("T_KDA").toString(),
                    KDA_Total = info.get("Total_KDA").toString(),
                    winrateTotal = info.get("Total_win").toString()
                )

                Log.d(TAG, summonerInfo.toString())

                summonerAdapt(this, summonerInfo)
            }

        }

    }


    companion object{
        private const val TAG = "MatchHistroy Test"
    }


}