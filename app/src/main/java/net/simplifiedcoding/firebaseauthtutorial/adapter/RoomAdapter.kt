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
        viewBinding.roomDescription.text = room.roomName
        // TODO : room.roomId를 어떻게 사용할지 정하기
        // TODO : roomName을 어떻게 적용할지도 정하기
        viewBinding.roomDescription.setOnClickListener {
            Log.d("RoomList Binding Test", "제대로 눌렸다!")
            var bundle = bundleOf(
                "roomId" to room.roomId
            )
            viewBinding.root.findNavController().navigate(R.id.action_roomHistory_to_chatRoomFragment, bundle)
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
