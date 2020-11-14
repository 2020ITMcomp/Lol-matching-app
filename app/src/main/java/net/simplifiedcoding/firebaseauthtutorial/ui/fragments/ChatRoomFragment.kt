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


class ChatRoomFragment : Fragment() {

    private lateinit var binding : FragmentChatRoomBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var recyclerView : RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var uid : String
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
        populateData()


        // 생성되어야지만 이 fragment로 넘어오므로, 무조건 roomId가 존재한다.
        val roomId = arguments!!.getString("roomId")
        val mUser = FirebaseAuth.getInstance().currentUser
        uid = mUser!!.uid


        val roomMessageRef = getRoomMessageRef(roomId)

        roomMessageRef.get()
            .addOnSuccessListener {
                Log.d(Companion.TAG, "Messages metadata : ${it.metadata}")
            }.addOnFailureListener { e ->
                Log.w(Companion.TAG, "Error adding document", e)
            }

//        roomMessageRef.add()



        binding.sendButton.setOnClickListener {

            val message = Message(uid = uid, text_message_body = binding.messageText.text.toString(),
                text_message_name = "TEMP", timeStamp = System.currentTimeMillis())

            roomMessageRef.add(message)
                .addOnSuccessListener {
                    Log.d(Companion.TAG, "Message sending is successful : ${it.id}")
                }.addOnFailureListener{ e ->
                    Log.w(Companion.TAG, "Error Message Sending", e)
                }

            messageAdapter.add(SendMessageItem(message))
            // After sending message
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
        val data = listOf<Message>()
        data.forEach {
            if(it.uid == uid){ // 직접 보낸 것이라면
//                messageAdapter.add()
            }
        }
    }

    companion object {
        private const val TAG = "ChatRoomFragment TEST"
    }

}