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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.simplifiedcoding.firebaseauthtutorial.adapter.AlarmMessageItem
import net.simplifiedcoding.firebaseauthtutorial.adapter.ReceiveMessageItem
import net.simplifiedcoding.firebaseauthtutorial.utils.*


class ChatRoomFragment : Fragment() {

    private lateinit var binding : FragmentChatRoomBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var recyclerView : RecyclerView
    private lateinit var roomMessageRef : CollectionReference
    private val messageAdapter = GroupAdapter<GroupieViewHolder>()

    private lateinit var nickname : String
    private lateinit var roomId : String
    private lateinit var uid : String

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
        nickname = arguments!!.get("nickname").toString()
        val type = arguments!!.get("type") as Int

        val mUser = FirebaseAuth.getInstance().currentUser
        uid = mUser!!.uid
        roomMessageRef = getRoomMessageRef(roomId)

        populateData(type)

        binding.sendButton.setOnClickListener {

            val message = Message(
                uid = uid,
                text_message_body = binding.messageText.text.toString(),
                text_message_name = nickname,
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

        binding.chatout.setOnClickListener {
            android.app.AlertDialog.Builder(this.context).apply {
                setTitle("듀오 종료")
                setPositiveButton("네") {_, _ ->
                    outChatRoom()
                }
                setNegativeButton("아니요") {_, _ ->
                }
            }.create().show()
        }

        return binding.root
    }

    private fun outChatRoom(){
        val ref = getRoomRef(roomId)
        ref.get().addOnSuccessListener { room ->
            val userId1 = room.get("createUser").toString()
            val userId2 = room.get("enteredUser").toString()
            deleteRoomFromUser(userId1, roomId)
            deleteRoomFromUser(userId2, roomId)
        }
        ref.delete()

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

    private fun populateData(type : Int) {

        roomMessageRef.orderBy("timeStamp", Query.Direction.DESCENDING).get().addOnSuccessListener { messages ->
            for(message in messages){

                val messageId = message.getString("uid")!!
                if(messageId.equals(uid)){
                    messageAdapter.add(SendMessageItem(snapshotToMessage(message)))
                }else if(messageId.equals("alarm")){
                    messageAdapter.add(AlarmMessageItem(snapshotToMessage(message)))
                }else{
                    messageAdapter.add(ReceiveMessageItem(snapshotToMessage(message),this.context))
                }
            }
        }



    }

    companion object {
        private const val TAG = "ChatRoomFragment TEST"

    }
}