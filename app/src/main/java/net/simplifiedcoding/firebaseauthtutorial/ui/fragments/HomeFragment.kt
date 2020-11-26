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
import com.squareup.picasso.Picasso
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.Delay
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentHomeBinding
import net.simplifiedcoding.firebaseauthtutorial.ui.HomeActivity
import net.simplifiedcoding.firebaseauthtutorial.ui.LoginActivity
import net.simplifiedcoding.firebaseauthtutorial.utils.*


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var db : FirebaseFirestore
    private lateinit var mUser : FirebaseUser
    private lateinit var nickname : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mUser = FirebaseAuth.getInstance().currentUser!!
        db = FirebaseFirestore.getInstance()
        getUserNicknameRef(mUser.uid).addOnSuccessListener {info ->
            nickname = info.get("nickname").toString()
            InputUserAbstract()
        }
        //Log.d(TAG, mUser.displayName) TODO : 일단은 사용자의 닉네임을 입력하도록 해야한다.


        binding.checkRoomButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_roomHistory, bundleOf( "nickname" to nickname))
        }
        binding.searchRoomButton.setOnClickListener {
            dialogBuilder()
        }
        binding.matchHistoryButton.setOnClickListener {

            Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_matchHistory,
                bundleOf(
                    "nickname" to binding.summonerNickname.text
                ))
        }



        return binding.root
    }

    private fun dialogBuilder(){
        var summonerBuilder = AlertDialog.Builder(context)
            .setTitle("Choice Your Lane")
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
                    if(partnerLane == -1)return@OnClickListener

                    var bundle = bundleOf(
                        "summonerLane" to summonerLane,
                        "partnerLane" to partnerLane,
                        "nickname" to nickname
                    )
                    Log.d(TAG, bundle.toString())
                    Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_searchWaiting, bundle)

                })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    return@OnClickListener
                }).show()
    }
    private fun InputUserAbstract() {

        binding.apply {

            getSummonerInfoRef(nickname).get().addOnSuccessListener { info ->

                Firebase.storage.reference.child("Tier/${info.get("tier").toString()}.png").downloadUrl.addOnSuccessListener { uri ->
                    Picasso.get().load(uri).into(binding.tiericon)
                    binding.summonernickname.text = info.get("name").toString()
                    binding.summonertier.text = info.get("tier").toString()
                    binding.bottomwinrate.text = (info.get("B_Win")as Double).times(100).toString() + "%"
                    binding.middlewinrate.text = (info.get("M_Win")as Double).times(100).toString() + "%"
                    binding.junglewinrate.text = (info.get("J_Win")as Double).times(100).toString() + "%"
                    binding.topwinrate.text = (info.get("T_Win")as Double).times(100).toString() + "%"
                    binding.supportwinrate.text = (info.get("S_Win")as Double).times(100).toString() + "%"

                    binding.bottomkda.text = info.get("B_KDA").toString()
                    binding.middlekda.text = info.get("M_KDA").toString()
                    binding.junglekda.text = info.get("J_KDA").toString()
                    binding.topkda.text = info.get("T_KDA").toString()
                    binding.supportkda.text = info.get("S_KDA").toString()
                }


            }

        }

    }

    companion object{

        private const val TAG = "HomeFragment Test"
        private var summonerLane : Int = -1
        private var partnerLane : Int = -1

    }




}