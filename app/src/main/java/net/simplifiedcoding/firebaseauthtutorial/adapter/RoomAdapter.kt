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
import net.simplifiedcoding.firebaseauthtutorial.databinding.RoomListBinding
import java.text.SimpleDateFormat


class RoomHolder(private val room: Room) : BindableItem<RoomListBinding>() {
    override fun getLayout(): Int {
        return R.layout.room_list
    }

    override fun bind(viewBinding: RoomListBinding, position: Int) {
        val roomName = room.timeStamp + " " + laneInterpretation(room.summonerLane) + " : " + laneInterpretation(room.partnerLane) + " 듀오"

        viewBinding.roomDescription.text = roomName
        viewBinding.roomDescription.setOnClickListener {
            var bundle = bundleOf(
                "roomId" to room.roomId,
                "nickname" to room.nickname,
                "type" to room.type
            )
            viewBinding.root.findNavController().navigate(R.id.action_roomHistory_to_chatRoomFragment, bundle)
        }
    }

    fun laneInterpretation(lane : Long) : String{

        return if(lane.equals(0L)){
            "탑"
        }else if(lane.equals(1L)){
            "정글"
        }else if(lane.equals(2L)){
            "미드"
        }else if(lane.equals(3L)){
            "원딜"
        }else if(lane.equals(4L)){
            "서포터"
        }else{
            "None"
        }
    }
//
//    private fun createOnClickListener(binding : RoomListBinding, videoId: String): View.OnClickListener {
//        return View.OnClickListener {
//            val directions = MainFragmentDirections.viewVideoDetails(videoId)
//            val extras = FragmentNavigatorExtras(
//                binding.videoTitle to "title_$videoId",
//                binding.videoDuration to "duration_$videoId",
//                binding.videoThumbnail to "thumbnail_$videoId")
//            it.findNavController().navigate(directions, extras)
//        }
//    }
}
