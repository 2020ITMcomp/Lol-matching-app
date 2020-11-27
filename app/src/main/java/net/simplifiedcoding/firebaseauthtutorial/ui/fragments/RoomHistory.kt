package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.adapter.Room
import net.simplifiedcoding.firebaseauthtutorial.adapter.RoomHolder
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentHomeBinding
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentRoomHistoryBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.getRoomListRef
import java.text.SimpleDateFormat


class RoomHistory : Fragment() {

    private lateinit var binding : FragmentRoomHistoryBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var mUser : FirebaseUser
    private lateinit var recyclerView: RecyclerView
    private val roomAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var uid : String
    private lateinit var nickname : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRoomHistoryBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        mUser = FirebaseAuth.getInstance().currentUser!!
        uid = mUser.uid
        nickname = arguments!!.getString("nickname")

        recyclerView = binding.roomList.apply {
            setHasFixedSize(true)
            adapter = roomAdapter
        }

        setList() // TODO : 나중에 livedata를 적용해서 하는 방법을 찾아볼까.
        roomAdapter.clear()

        return binding.root
    }

    private fun setList() {
        getRoomListRef(uid).orderBy("timeStamp", Query.Direction.DESCENDING).get().addOnSuccessListener { rooms ->
            for(room in rooms){

                val roomObj = Room(
                    nickname = nickname,
                    roomId = room.getString("roomId")!!,
                    timeStamp = room.getString("timeStamp")!!,
                    summonerLane = room.getLong("summonerLane")!!,
                    partnerLane = room.getLong("partnerLane")!!,
                    type = room.getLong("type")!!,
                    closed = room.getBoolean("closed")!!
                )//roomId로 Name을 설정한 것은 임시
                roomAdapter.add(RoomHolder(roomObj))
            }
        }.addOnFailureListener { e ->
            Log.w(TAG, "Error ref to FireStore about RoomList", e)
        }

    }

    companion object{
        private const val TAG = "RoomHistory TEST"
    }

}