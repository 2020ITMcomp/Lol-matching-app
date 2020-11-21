package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentGameHistoryBinding


class GameHistory : Fragment() {

    private lateinit var binding : FragmentGameHistoryBinding
    private lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGameHistoryBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()

        val data = db.collection("user").document("하계동 맘스터치").get()



        return binding.root
    }


}