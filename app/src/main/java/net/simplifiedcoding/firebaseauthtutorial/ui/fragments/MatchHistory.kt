package net.simplifiedcoding.firebaseauthtutorial.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import net.simplifiedcoding.firebaseauthtutorial.adapter.Match
import net.simplifiedcoding.firebaseauthtutorial.adapter.MatchHolder
import net.simplifiedcoding.firebaseauthtutorial.adapter.SummonerInfo
import net.simplifiedcoding.firebaseauthtutorial.adapter.summonerAdapt
import net.simplifiedcoding.firebaseauthtutorial.databinding.FragmentMatchHistoryBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.getSummonerGamedataRef
import net.simplifiedcoding.firebaseauthtutorial.utils.getSummonerInfoRef
import net.simplifiedcoding.firebaseauthtutorial.utils.getUserNicknameRef
import net.simplifiedcoding.firebaseauthtutorial.utils.userRenewalHistory
import org.json.JSONArray
import java.lang.reflect.Type
import kotlin.math.roundToInt


class MatchHistory : Fragment() {

    private lateinit var binding : FragmentMatchHistoryBinding
    private lateinit var nickname : String
    private lateinit var uid : String
    private lateinit var recyclerView : RecyclerView
    private val matchAdapter = GroupAdapter<GroupieViewHolder>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMatchHistoryBinding.inflate(inflater, container, false)
        uid = FirebaseAuth.getInstance().uid.toString()
        nickname = arguments!!.get("nickname").toString()

        InputUserAbstract()

        recyclerView = binding.historyView.apply {
            setHasFixedSize(true)
            adapter = matchAdapter
        }

        historyPopulate()

        binding.renewalButton.setOnClickListener {
            userRenewalHistory(uid)
        }



        return binding.root
    }

    private fun getParticipantId(list : ArrayList<Map<*,*>>) : Int{

        var participantId : Int = -1

        for(map in list){
            participantId++
            val info = map.get("player") as Map<String, *>
            val summonerName = info.get("summonerName") as String

            if(summonerName.equals(nickname)){
                Log.d(TAG, "소환사 이름 얻기")
                Log.d(TAG, summonerName + " " + participantId.toString())
                break
            }
        }
        return participantId
    }

    private fun historyPopulate() {

        getSummonerGamedataRef(nickname).orderBy("gameId", Query.Direction.DESCENDING).limit(20).addSnapshotListener{ totalGamedata, firebaseFirestoreException ->
//            totalGamedata!!.documents
            for(gamedata in totalGamedata!!){

                Log.d(TAG, gamedata.id)
                // 참가자 번호 얻는 로직
                val participantsIdentity = gamedata.get("participantIdentities") as ArrayList<Map<*,*>>
                var participantId = getParticipantId(participantsIdentity)

                val participant = gamedata.get("participants") as ArrayList<Map<String, *>>
                val participantInfo = participant.get(participantId)
                val gameStat = participantInfo.get("stats") as Map<String, *>

                // 아래부터는 데이터 얻는 작업 //

                val gameDuration= gamedata.get("gameDuration") as Long
                val gameCreation= gamedata.get("gameCreation") as Long

                val championId= participantInfo.get("championId") as Long
                val spell1Id= participantInfo.get("spell1Id") as Long
                val spell2Id= participantInfo.get("spell2Id") as Long

                val win= gameStat.get("win") as Boolean
                val champLevel= gameStat.get("champLevel") as Long
                val kills= gameStat.get("kills") as Long
                val death= gameStat.get("deaths") as Long
                val assist= gameStat.get("assists") as Long

                val zero : Long = 0
                var kda : Float
                if(death.equals(zero))kda = 100000F
                else {
                    kda= kills.plus(assist).toFloat().div(death)
                    kda = (kda * 100).roundToInt() / 100f
                }


                val cs1 = gameStat.get("neutralMinionsKilled") as Long
                val cs2 = gameStat.get("neutralMinionsKilledEnemyJungle") as Long
                val cs3 = gameStat.get("neutralMinionsKilledTeamJungle") as Long
                val cs = cs1 + cs2 + cs3

                val goldEarned= gameStat.get("goldEarned") as Long
                val killSpring= gameStat.get("largestMultiKill") as Long

                // 데이터 작업 끝 !

                matchAdapter.add(MatchHolder(Match(
                    gameDuration = gameDuration,
                    gameCreation = gameCreation,
                    championId = championId,
                    spell1Id = spell1Id,
                    spell2Id = spell2Id,
                    win = win,
                    champLevel = champLevel,
                    kills = kills,
                    death = death,
                    assist = assist,
                    kda = kda,
                    cs = cs,
                    goldEarned = goldEarned,
                    killSpring = killSpring
                )))

            }

            // TODO : for문에 바깥에다가 끝나고 나서 gone 하나 넣어주면 될거 같은데

        }

    }

    private fun InputUserAbstract() {

        binding.apply {

            getSummonerInfoRef(nickname).get().addOnSuccessListener { info ->

                Firebase.storage.reference.child("Tier/${info.get("tier").toString()}.png").downloadUrl.addOnSuccessListener { uri ->
                    Picasso.get().load(uri).into(binding.profileicon)
                    binding.userTier.text = info.get("tier").toString()
                    binding.name.text = info.get("name").toString()
                    binding.level.text = ("Level : " + info.get("summonerLevel").toString())
                    binding.typeB.text = info.get("B_Feature").toString()
                    binding.typeM.text = info.get("M_Feature").toString()
                    binding.typeJ.text = info.get("J_Feature").toString()
                    binding.typeT.text = info.get("T_Feature").toString()
                    binding.typeS.text = info.get("S_Feature").toString()

                    binding.winrateB.text = (info.get("B_Win")as Double).times(100).toString() + "%"
                    binding.winrateM.text = (info.get("M_Win")as Double).times(100).toString() + "%"
                    binding.winrateJ.text = (info.get("J_Win")as Double).times(100).toString() + "%"
                    binding.winrateT.text = (info.get("T_Win")as Double).times(100).toString() + "%"
                    binding.winrateS.text = (info.get("S_Win")as Double).times(100).toString() + "%"
                    binding.winrateTotal.text = "승률 : "  + ((info.get("Total_Win")as Double).times(100).toString() + "%")

                    binding.KDAB.text = info.get("B_KDA").toString()
                    binding.KDAM.text = info.get("M_KDA").toString()
                    binding.KDAJ.text = info.get("J_KDA").toString()
                    binding.KDAT.text = info.get("T_KDA").toString()
                    binding.KDAS.text = info.get("S_KDA").toString()
                    binding.KDATotal.text = ("KDA : " + info.get("Total_KDA").toString())
                }


            }

        }

    }


    companion object{
        private const val TAG = "MatchHistroy Test"
    }


}