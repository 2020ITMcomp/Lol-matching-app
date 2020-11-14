package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentHomeBinding
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentRoomHistoryBinding


class RoomHistory : Fragment() {

    private lateinit var binding : FragmentRoomHistoryBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var mUser : FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRoomHistoryBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        mUser = FirebaseAuth.getInstance().currentUser!!

        // Room에 대한 정보들을 담아서 넣기
        // TODO : DB에서 user id를 base로 해서 room들을 뽑아오고, 다른 유저에 대한 이름으로 방을 만들어서 사용하도록 하자.


        return binding.root
    }

}