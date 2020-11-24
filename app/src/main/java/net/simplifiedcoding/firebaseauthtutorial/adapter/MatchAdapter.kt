package net.simplifiedcoding.firebaseauthtutorial.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.databinding.BindableItem
import kotlinx.android.synthetic.main.fragment_room_history.*
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.ItemMatchBinding
import net.simplifiedcoding.firebaseauthtutorial.databinding.RoomListBinding
import java.text.SimpleDateFormat


class MatchHolder(private val match: Match) : BindableItem<ItemMatchBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_match
    }

    override fun bind(viewBinding: ItemMatchBinding, position: Int) {

//        viewBinding.champion = match.championId // 이건 이미지 넣어야함
        match.spell1Id
        match.spell2Id
        if(match.win)viewBinding.winlose.text = "승"
        else viewBinding.winlose.text = "패"
        viewBinding.level.text = "Level : " + match.champLevel.toString()
        viewBinding.kill.text = match.kills.toString()
        viewBinding.death.text = match.death.toString()
        viewBinding.assist.text = match.assist.toString()

        val check = 100000F
        if(match.kda.equals(check))viewBinding.kda.text = "Infinity"
        else viewBinding.kda.text = "KDA : " + match.kda.toString()
        viewBinding.cs.text = "CS : " + match.cs.toString()
        viewBinding.gold.text = "Gold : " + match.goldEarned.toString()
//        if(match.killSpring.equals(Long(2))){
//
//        }
        Log.d("연속킬 찾기", match.killSpring.toString())
        val doubleKill : Long = 2
        val tripleKill : Long = 3
        val quardraKill : Long = 4
        val pentaKill : Long = 5
        if(match.killSpring.equals(doubleKill)){
            viewBinding.killspring.text = "더블킬"
        }else if(match.killSpring.equals(tripleKill)){
            viewBinding.killspring.text = "트리플킬"
        }else if(match.killSpring.equals(quardraKill)){
            viewBinding.killspring.text = "쿼드라킬"
        }else if(match.killSpring.equals(pentaKill)){
            viewBinding.killspring.text = "펜타킬"
        }else{
            viewBinding.killspring.text = "Nope"
        }

        val min = match.gameDuration.div(60)
        val second = match.gameDuration.rem(60)
        viewBinding.gameduration.text = " 게임시간 " + min.toString() + "분 " + second.toString() +"초" // 시간으로 수정해야함
//        viewBinding.gameTime.text = match.gameCreation.toString()
        viewBinding.gameTime.text = SimpleDateFormat("yyyy년 MM월 dd일 - H:mm").format(match.gameCreation.toLong())
    }
}
