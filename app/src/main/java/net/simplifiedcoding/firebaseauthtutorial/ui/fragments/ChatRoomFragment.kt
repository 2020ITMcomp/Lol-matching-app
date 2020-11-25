package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import net.simplifiedcoding.firebaseauthtutorial.adapter.Message
import net.simplifiedcoding.firebaseauthtutorial.adapter.SendMessageItem
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentChatRoomBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.getRoomMessageRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.simplifiedcoding.firebaseauthtutorial.adapter.ReceiveMessageItem
import net.simplifiedcoding.firebaseauthtutorial.utils.getRoomRef
import net.simplifiedcoding.firebaseauthtutorial.utils.snapshotToMessage


class ChatRoomFragment : Fragment() {

    private lateinit var binding : FragmentChatRoomBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var recyclerView : RecyclerView
    private lateinit var uid : String
    private lateinit var roomMessageRef : CollectionReference
    private val messageAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var nickname : String
    private lateinit var roomId : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "Successfully entering")
        binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        recyclerView = binding.chatView
        recyclerView.adapter = messageAdapter
        roomId = arguments!!.getString("roomId")
//        nickname = arguments!!.get("nickname").toString()

        val mUser = FirebaseAuth.getInstance().currentUser
        uid = mUser!!.uid
        roomMessageRef = getRoomMessageRef(roomId)

        populateData()

        binding.sendButton.setOnClickListener {

            val message = Message(
                uid = uid,
                text_message_body = binding.messageText.text.toString(),
                text_message_name = "TEMP",
//                timeStamp = System.currentTimeMillis() + 32_400_000) // 시차 9시간 적용
                timeStamp = System.currentTimeMillis())


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

        // TODO : messageref에서 얻는 거는, 시간 반대순으로 띄워줘야한다.

        getRoomRef(roomId).get().addOnSuccessListener {room ->

            roomMessageRef.orderBy("timeStamp", Query.Direction.DESCENDING).get().addOnSuccessListener { messages ->
                for(message in messages){

                    if(uid.equals((message.getString("uid")))){
                        messageAdapter.add(SendMessageItem(snapshotToMessage(message)))
                    }else{
                        var nickname : String

                        if(uid.equals(room.get("createUser")))nickname = room.get("enteredUserNickname").toString()
                        else nickname = room.get("createUserNickname").toString()

                        messageAdapter.add(ReceiveMessageItem(snapshotToMessage(message),this.context, nickname))
                    }
                }
            }
        }



    }

    companion object {
        private const val TAG = "ChatRoomFragment TEST"

    }
}