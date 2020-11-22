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
        viewBinding.winlose.text = match.winlose.toString()
        viewBinding.gameduration.text = match.gameDuration.toString()
//        viewBinding.champion = match.champion.toString() // TODO : 챔피언 이미지를 다운받아서 넣어줘야함
        viewBinding.kill.text = match.kill.toString()
        viewBinding.death.text = match.death.toString()
        viewBinding.assist.text = match.assist.toString()
//    val kda : Double,
        viewBinding.level.text = match.level.toString()
        viewBinding.cs.text = match.cs.toString()
        viewBinding.gold.text = match.gold.toString()
//        viewBinding. = match.firstSpell.toString()
//        viewBinding. = match.secondSpell.toString()
        viewBinding.killspring.text = match.killSpring.toString()
        viewBinding.gameTime.text = match.timeStamp.toString()
    }
}
