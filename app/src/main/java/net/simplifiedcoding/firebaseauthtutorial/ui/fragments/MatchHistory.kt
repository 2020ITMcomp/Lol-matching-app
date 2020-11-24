package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.app.ActionBar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentMatchHistoryBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.getSummonerInfoRef
import net.simplifiedcoding.firebaseauthtutorial.utils.getUserNicknameRef
import net.simplifiedcoding.firebaseauthtutorial.utils.userRenewalHistory


class MatchHistory : Fragment() {

    private lateinit var binding : FragmentMatchHistoryBinding
    private lateinit var uid : String
    private lateinit var recyclerView : RecyclerView
    private val matchAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMatchHistoryBinding.inflate(inflater, container, false)
        uid = FirebaseAuth.getInstance().uid.toString()


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
        binding.userAbstract.apply {

            getUserNicknameRef(uid).addOnSuccessListener {
//                it.
                // 최종적으로 받은 다음에

            }.addOnFailureListener {
                // TODO 돌아가게 하기
            }
//            val summonerInfo = getSummonerInfoRef()
        }

    }


}