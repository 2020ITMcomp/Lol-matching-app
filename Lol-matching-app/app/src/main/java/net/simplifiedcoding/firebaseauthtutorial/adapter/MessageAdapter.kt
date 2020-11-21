package net.simplifiedcoding.firebaseauthtutorial.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.databinding.BindableItem
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.ItemMessageReceivedBinding
import net.simplifiedcoding.firebaseauthtutorial.databinding.ItemMessageSentBinding
import java.text.SimpleDateFormat

class SendMessageItem(private val message: Message) : BindableItem<ItemMessageSentBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_sent
    }

    override fun bind(viewBinding: ItemMessageSentBinding, position: Int) {
        viewBinding.textMessageBody.text = message.text_message_body
        viewBinding.textMessageTime.text = SimpleDateFormat("HH:mm").format(message.timeStamp.toLong())
    }
}

class ReceiveMessageItem(private val message: Message) : BindableItem<ItemMessageReceivedBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_received
    }

    override fun bind(viewBinding: ItemMessageReceivedBinding, position: Int) {
        viewBinding.textMessageBody.text = message.text_message_body
        viewBinding.textMessageName.text = message.text_message_name
        viewBinding.textMessageTime.text = SimpleDateFormat("HH:mm").format(message.timeStamp.toLong())

    }
}
