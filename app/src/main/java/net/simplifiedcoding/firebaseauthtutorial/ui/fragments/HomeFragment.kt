package net.simplifiedcoding.firebaseauthtutorial.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.view.*
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var mUser : FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mUser = FirebaseAuth.getInstance().currentUser!!
        db = FirebaseFirestore.getInstance()

        //Log.d(TAG, mUser.displayName) TODO : 일단은 사용자의 닉네임을 입력하도록 해야한다.

        binding.createRoomButton.setOnClickListener{
            createNewRoom()
        }
        binding.checkRoomButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_roomHistory)
        }
        binding.searchRoomButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_searchWaiting)
        }



        return binding.root
    }


    private fun createNewRoom(){

        // TODO : 유저의 선호 라인 및 원하는 상대방 라인에 대한 정보를 가져와서 추가해주어야 한다.
        var roomId : String
        val room = hashMapOf(
            "createUser" to mUser!!.uid,
            "userPosition" to "Jungle",
            "otherPosition" to "Mid lane",
            "timeStamp" to System.currentTimeMillis().toString(),
            "isClosed" to false
        )

        // TODO : 지금 생각하는 방식으로는 비합리적이긴 한데,
        // 그냥 Closed room과 opened room 을 모두 하나의 room collection으로 사용하도록 하자. (임시)

        val ref = db.collection("rooms").add(room).addOnSuccessListener { documentReference ->
            Log.d(TAG, "Successfully created the room : ${documentReference.id}")
            
            roomId = documentReference.id
            referenceToUser(roomId)

            var bundle = bundleOf(
                "roomId" to roomId
            )
            // Navigate to chatRoom
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_chatRoomFragment, bundle)

        }.addOnFailureListener { e ->
            Log.w(TAG, "Can not create a new room!", e)
            // TODO 만들지 못했을 때, 다시 돌아가도록 하는 로직 작성.
        }

    }

    private fun referenceToUser(roomId : String){

        val room = hashMapOf( // 추가적으로 데이터가 필요한지는 생각해봐야 할 듯.
            "roomId" to roomId
        )

        val userRef = db.collection("users").document(mUser.uid)
            .collection("enteredRoom").add(room)
            .addOnSuccessListener { it ->
                Log.d(TAG, "Successfully adding a room to the user : ${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Can not adding a room to the user!", e)
            }

    }

    companion object{

        private const val TAG = "HomeFragment Test"

    }




}
