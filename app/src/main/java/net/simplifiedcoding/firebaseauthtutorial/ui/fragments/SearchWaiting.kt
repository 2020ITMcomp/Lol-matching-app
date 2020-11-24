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
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentHomeBinding
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentSearchWaitingBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.*


class SearchWaiting : Fragment() {

    private lateinit var binding : FragmentSearchWaitingBinding
    private lateinit var mUser : FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchWaitingBinding.inflate(inflater,container, false)
        mUser = FirebaseAuth.getInstance().currentUser!!

        val image:ImageView = binding.root.waiting
        Glide.with(this).asGif().load(R.raw.image).into(image)

        val summonerLane = arguments!!.getInt("summonerLane")
        val partnerLane = arguments!!.getInt("partnerLane")

        canMatch(summonerLane, partnerLane)


        matchingStart()








        return binding.root

    }

    private fun canMatch(summonerLane : Int, partnerLane : Int){

        var ref = getMatchingRoomListRef(partnerLane, summonerLane).addOnSuccessListener {
            if(it.size() > 0){ // 매칭되는 방이 있는 것
                it.forEach { room ->
//                    Log.d(TAG, room.toString())

                }
            }else{ // 맞는 방이 없을 경우.
                createRoom(summonerLane, partnerLane)
            }
        }
    }

    private fun createRoom(summonerLane: Int, partnerLane: Int){
        addNewRoom(mUser.uid, summonerLane, partnerLane).addOnSuccessListener { documentReference ->
            Log.d("createNewRoom", "Successfully created the room : ${documentReference.id}")
            val roomId = documentReference.id
            addRoomToUser(mUser.uid, roomId)


            // 닉네임 받아서 방으로 넘기기
            getUserNicknameRef(mUser.uid).addOnSuccessListener {
                var bundle = bundleOf(
                    "roomId" to roomId,
                    "nickname" to it.get("nickname").toString()
                )
                // Navigate to chatRoom
                Navigation.findNavController(binding.root).navigate(R.id.action_searchWaiting_to_chatRoomFragment, bundle)

            }

        }.addOnFailureListener { e ->
            Log.w("createNewRoom", "Can not create a new room!", e)
        }

    }

    private fun matchingStart() {

//        val roomRef = db.collection("rooms").get()
//            .addOnSuccessListener { documents ->
//                for(document in documents){
//                    // TODO : 여기서 매칭 데이터에 맞는 방을 찾아야 한다.
//                    if (document != null) {
//                        //Log.d('d', "DocumentSnapshot data: ${document.data}")
//                    } else {
//                        //Log.d(TAG, "No such document")
//                    }
//                }
//
//            }.addOnFailureListener { e ->
//                //Log.w(TAG, "DB room reference ERROR")
//            }

    }

    companion object{
        private const val TAG = "SearchWaiting Fragment TEST"
    }


}