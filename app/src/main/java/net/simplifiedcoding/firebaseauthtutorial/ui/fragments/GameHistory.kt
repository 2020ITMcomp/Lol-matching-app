package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentGameHistoryBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.userRenewalHistory


class GameHistory : Fragment() {

    private lateinit var binding : FragmentGameHistoryBinding
    private lateinit var mUser : FirebaseUser
    private lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGameHistoryBinding.inflate(inflater, container, false)
        mUser = FirebaseAuth.getInstance().currentUser!!
        db = FirebaseFirestore.getInstance()

        val data = db.collection("user").document("하계동 맘스터치").get()

        binding.renewalButton.setOnClickListener {


            userRenewalHistory(mUser.uid)

        }



        return binding.root
    }


}