package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.adapter.Message
import net.simplifiedcoding.firebaseauthtutorial.adapter.SendMessageItem
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentChatRoomBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.getRoomMessageRef
import java.sql.Time
import java.sql.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.simplifiedcoding.firebaseauthtutorial.adapter.ReceiveMessageItem
import net.simplifiedcoding.firebaseauthtutorial.utils.snapshotToMessage
import java.text.SimpleDateFormat


class ChatRoomFragment : Fragment() {

    private lateinit var binding : FragmentChatRoomBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var recyclerView : RecyclerView
    private lateinit var uid : String
    private lateinit var roomMessageRef : CollectionReference
    private val messageAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "Successfully entering")
        binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        recyclerView = binding.chatView
        recyclerView.adapter = messageAdapter

        val roomId = arguments!!.getString("roomId")
        val mUser = FirebaseAuth.getInstance().currentUser
        uid = mUser!!.uid
        roomMessageRef = getRoomMessageRef(roomId)

        populateData()

        binding.sendButton.setOnClickListener {

            val message = Message(uid = uid, text_message_body = binding.messageText.text.toString(),
                text_message_name = "TEMP", timeStamp = System.currentTimeMillis() + 32_400_000) // 시차 9시간 적용

            roomMessageRef.add(message)
                .addOnSuccessListener {
                    Log.d(Companion.TAG, "Message sending is successful : ${it.id}")
                }.addOnFailureListener{ e ->
                    Log.w(Companion.TAG, "Error Message Sending", e)
                }

            messageAdapter.add(SendMessageItem(message))
            binding.messageText.text.clear()
            receiveAutoResponse()
        }

        return binding.root
    }

    private fun receiveAutoResponse() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)

            // TODO : 받아야댐
//            val receive = Message(text = "Salut l'amis j'espere que vous allez bien, je suis tres bien j'ai manger to day", sendBy = "me")
//            val receiveItem = ReceiveMessageItem(receive)
//
//            messageAdapter.add(receiveItem)
        }
    }

    private fun populateData() {

        val messages = roomMessageRef.get()
            .addOnSuccessListener { messages ->
                for(message in messages){

                    // TODO : 일단 논리는 제대로 작성해놨는데, 확인을 제대로 못해서 오류가 날 확률이 높음.
                    val messageUid : String = message.getString("uid")!!

                    if(uid.equals(messageUid)){
                        messageAdapter.add(SendMessageItem(snapshotToMessage(message)))
                    }else{
                        messageAdapter.add(ReceiveMessageItem(snapshotToMessage(message)))
                    }
                }
            }.addOnFailureListener { e ->
                Log.w(Companion.TAG, "Error adding document", e)
            }


    }

    companion object {
        private const val TAG = "ChatRoomFragment TEST"
    }

}