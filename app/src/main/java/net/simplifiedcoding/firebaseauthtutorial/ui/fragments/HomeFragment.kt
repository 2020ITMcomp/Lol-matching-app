package net.simplifiedcoding.firebaseauthtutorial.ui.fragments


import android.app.AlertDialog
import android.content.DialogInterface
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
import kotlinx.coroutines.Delay
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentHomeBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.addNewRoom
import net.simplifiedcoding.firebaseauthtutorial.utils.addRoomToUser
import net.simplifiedcoding.firebaseauthtutorial.utils.getUserNicknameRef


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

//            addNewRoom(mUser.uid).addOnSuccessListener { documentReference ->
//                Log.d("createNewRoom", "Successfully created the room : ${documentReference.id}")
//                val roomId = documentReference.id
//                addRoomToUser(mUser.uid, roomId)
//
//                var bundle = bundleOf(
//                    "roomId" to roomId
//                )
//                // Navigate to chatRoom
//                Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_chatRoomFragment, bundle)
//            }.addOnFailureListener { e ->
//                Log.w("createNewRoom", "Can not create a new room!", e)
//            }
        }
        binding.checkRoomButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_roomHistory)
        }
        binding.searchRoomButton.setOnClickListener {
            dialogBuilder()
        }
        binding.matchHistoryButton.setOnClickListener {

            // TODO : 소환사 이름을 입력한 것이 없다면 누를 수 없도록
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_matchHistory,
                bundleOf(
                    "nickname" to binding.summonerNickname.text
                ))
        }


        return binding.root
    }

    private fun dialogBuilder(){
        var summonerBuilder = AlertDialog.Builder(context)
            .setTitle("CHoice Your Lane")
            .setSingleChoiceItems(R.array.lane, -1,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    summonerLane = i
                })
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, id ->
                    // 자신의 라인 선택이 완료 되었을 때,
                    if(summonerLane == -1)return@OnClickListener
                    partnerDialogBuilder()
                })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    return@OnClickListener
                }).show()

    }

    private fun partnerDialogBuilder(){
        var partnerBuilder = AlertDialog.Builder(context)
            .setTitle("Choice Partner Lane")
//                .setMessage("Your position")
            .setSingleChoiceItems(R.array.lane, -1,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    partnerLane = i
                })
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, id ->
                    // 라인 선택이 완료 된 후에, lane에 대한 데이터를 넘겨주고 -> Navigation
                    if(partnerLane == -1)return@OnClickListener

                    getUserNicknameRef(mUser.uid).addOnSuccessListener {
                        var bundle = bundleOf(
                            "summonerLane" to summonerLane,
                            "partnerLane" to partnerLane,
                            "nickname" to it.get("nickname").toString()
                        )
                        Log.d(TAG, bundle.toString())
                        Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_searchWaiting, bundle)
                    }



                })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    return@OnClickListener
                }).show()
    }

    companion object{

        private const val TAG = "HomeFragment Test"
        private var summonerLane : Int = -1
        private var partnerLane : Int = -1

    }




}