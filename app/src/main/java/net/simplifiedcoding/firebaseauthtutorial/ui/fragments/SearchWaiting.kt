package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ViewTarget
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_search_waiting.view.*
import kotlinx.android.synthetic.main.hojin_1.*
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.adapter.Message
import net.simplifiedcoding.firebaseauthtutorial.adapter.Room
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentHomeBinding
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentSearchWaitingBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.*
import java.text.SimpleDateFormat


class SearchWaiting : Fragment() {

    private lateinit var binding : FragmentSearchWaitingBinding
    private lateinit var mUser : FirebaseUser
    private lateinit var nickname : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchWaitingBinding.inflate(inflater,container, false)
        mUser = FirebaseAuth.getInstance().currentUser!!

        val image:ImageView = binding.root.waiting
        Glide.with(this).asGif().load(R.raw.image).into(image)

        val summonerLane = arguments!!.getLong("summonerLane")
        val partnerLane = arguments!!.getLong("partnerLane")
        nickname = arguments!!.getString("nickname").toString()

        canMatch(summonerLane, partnerLane)

        return binding.root

    }

    private fun canMatch(summonerLane : Long, partnerLane : Long){

        getMatchingRoomListRef(partnerLane, summonerLane).addOnSuccessListener {rooms ->
            if(rooms.size() > 0){ // 매칭되는 방이 있는 것
                rooms.forEach { room -> // TODO : 여기서 매칭 알고리즘 적용해야함
                    room.reference.update(
                        hashMapOf(
                            "enteredUser" to mUser.uid,
                            "enteredUserNickname" to nickname,
                            "isClosed" to true))

                    addRoomToUser(
                        mUser.uid, Room(
                            roomId =  room.id,
                            timeStamp =  SimpleDateFormat("MM월 dd일, HH:mm").format(System.currentTimeMillis()),
                            summonerLane = summonerLane,
                            partnerLane = partnerLane,
                            type = 1,
                            nickname = nickname))

                    val message = Message(
                        uid = "alarm",
                        text_message_body = "${nickname}님이 입장하였습니다.",
                        text_message_name = nickname,
                        timeStamp = System.currentTimeMillis())
                    var bundle = bundleOf(
                        "roomId" to room.id,
                        "nickname" to nickname,
                        "type" to 1 as Int// enteredUser
                    )

                    getRoomMessageRef(room.id).add(message).addOnSuccessListener {
                        Navigation.findNavController(binding.root).navigate(R.id.action_searchWaiting_to_chatRoomFragment, bundle)
                    }


                }
            }else{ // 맞는 방이 없을 경우.
                createRoom(summonerLane, partnerLane)
            }
        }
    }

    private fun createRoom(summonerLane: Long, partnerLane: Long){
        addNewRoom(mUser.uid, nickname, summonerLane, partnerLane).addOnSuccessListener { documentReference ->
            Log.d("createNewRoom", "Successfully created the room : ${documentReference.id}")
            val roomId = documentReference.id
            addRoomToUser(
                mUser.uid, Room(
                    roomId =  roomId,
                    timeStamp =  SimpleDateFormat("MM월 dd일, HH:mm").format(System.currentTimeMillis()),
                    summonerLane = summonerLane,
                    partnerLane = partnerLane,
                    type = 0,
                    nickname = nickname
                ))

            val message = Message(
                uid = "alarm",
                text_message_body = "${nickname}님이 입장하였습니다.",
                text_message_name = nickname,
                timeStamp = System.currentTimeMillis())
            var bundle = bundleOf(
                "roomId" to roomId,
                "nickname" to nickname,
                "type" to 0 // createUser
            )

            getRoomMessageRef(roomId).add(message).addOnSuccessListener {
                Navigation.findNavController(binding.root).navigate(R.id.action_searchWaiting_to_chatRoomFragment, bundle)
            }
            // Navigate to chatRoom




        }.addOnFailureListener { e ->
            Log.w("createNewRoom", "Can not create a new room!", e)
        }

    }

    companion object{
        private const val TAG = "SearchWaiting Fragment TEST"
    }


}