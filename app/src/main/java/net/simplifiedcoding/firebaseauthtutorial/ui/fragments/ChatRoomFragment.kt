package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentChatRoomBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.getRoomMessageRef
import java.sql.Time
import java.sql.Timestamp


class ChatRoomFragment : Fragment() {

    private lateinit var binding : FragmentChatRoomBinding
    private lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "Successfully entering")
        binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()

        // 생성되어야지만 이 fragment로 넘어오므로, 무조건 roomId가 존재한다.
        val roomId = arguments!!.getString("roomId")

        val mUser = FirebaseAuth.getInstance().currentUser
        val uid = mUser!!.uid


        val roomMessageRef = getRoomMessageRef(roomId)

        roomMessageRef.get()
            .addOnSuccessListener {
                Log.d(Companion.TAG, "Messages metadata : ${it.metadata}")
            }.addOnFailureListener { e ->
                Log.w(Companion.TAG, "Error adding document", e)
            }

//        roomMessageRef.add()

        binding.sendButton.setOnClickListener {

            val message = hashMapOf(
                "userId" to uid,
                "text" to binding.messageText.text.toString(),
                "timeStamp" to System.currentTimeMillis()
            )

            roomMessageRef.add(message)
                .addOnSuccessListener {
                    Log.d(Companion.TAG, "Message sending is successful : ${it.id}")
                }.addOnFailureListener{ e ->
                    Log.w(Companion.TAG, "Error Message Sending", e)
                }

            // After sending message
            binding.messageText.text.clear()
        }



        return binding.root
    }

    companion object {
        private const val TAG = "ChatRoomFragment TEST"
    }

}