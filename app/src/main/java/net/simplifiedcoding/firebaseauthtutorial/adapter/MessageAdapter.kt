package net.simplifiedcoding.firebaseauthtutorial.adapter

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.xwray.groupie.databinding.BindableItem
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.databinding.ItemMessageAlarmBinding
import net.simplifiedcoding.firebaseauthtutorial.databinding.ItemMessageReceivedBinding
import net.simplifiedcoding.firebaseauthtutorial.databinding.ItemMessageSentBinding
import net.simplifiedcoding.firebaseauthtutorial.utils.getSummonerInfoRef
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

class AlarmMessageItem(private val message: Message) : BindableItem<ItemMessageAlarmBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_alarm
    }

    override fun bind(viewBinding: ItemMessageAlarmBinding, position: Int) {
        viewBinding.textMessageBody.text = message.text_message_body
        viewBinding.textMessageTime.text = SimpleDateFormat("HH:mm").format(message.timeStamp.toLong())
    }
}

class ReceiveMessageItem(private val message: Message, val context: Context?) : BindableItem<ItemMessageReceivedBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_message_received
    }

    override fun bind(viewBinding: ItemMessageReceivedBinding, position: Int) {
        viewBinding.textMessageBody.text = message.text_message_body
        viewBinding.textMessageName.text = message.text_message_name
        viewBinding.textMessageTime.text = SimpleDateFormat("HH:mm").format(message.timeStamp.toLong())




        viewBinding.imageMessageProfile.setOnClickListener {view ->
            if (context != null) {

                CDialog(context,nickname = message.text_message_name).show()
            }

        }
    }

}


class CDialog
constructor(context: Context,nickname: String) : Dialog(context){


    init {

        getSummonerInfoRef(nickname).get().addOnSuccessListener { info ->

            // TODO : 서포터 추가
            findViewById<TextView>(R.id.name).text = info.get("name").toString()
            findViewById<TextView>(R.id.level).text = info.get("summonerLevel").toString()
            findViewById<TextView>(R.id.bottomkda).text = info.get("B_KDA").toString()
            findViewById<TextView>(R.id.topkda).text = info.get("T_KDA").toString()
            findViewById<TextView>(R.id.junglekda).text = info.get("J_KDA").toString()
            findViewById<TextView>(R.id.middlekda).text = info.get("M_KDA").toString()
            findViewById<TextView>(R.id.supportkda).text = info.get("S_KDA").toString()
            findViewById<TextView>(R.id.topwinrate).text = (info.get("T_Win") as Double).times(100).toString() + "%"
            findViewById<TextView>(R.id.junglewinrate).text = (info.get("J_Win") as Double).times(100).toString() + "%"
            findViewById<TextView>(R.id.middlewinrate).text = (info.get("M_Win")as Double).times(100).toString() + "%"
            findViewById<TextView>(R.id.bottomwinrate).text = (info.get("B_Win")as Double).times(100).toString() + "%"
            findViewById<TextView>(R.id.supportwinrate).text = (info.get("S_Win")as Double).times(100).toString() + "%"
            findViewById<TextView>(R.id.totalWinRate).text = (info.get("Total_Win") as Double).times(100).toString() + "%"
            findViewById<TextView>(R.id.topfeature).text = info.get("T_Feature").toString()
            findViewById<TextView>(R.id.junglefeature).text = info.get("J_Feature").toString()
            findViewById<TextView>(R.id.middlefeature).text = info.get("M_Feature").toString()
            findViewById<TextView>(R.id.bottomfeature).text = info.get("B_Feature").toString()
            findViewById<TextView>(R.id.supportfeature).text = info.get("S_Feature").toString()
            findViewById<TextView>(R.id.totalKDA).text = info.get("Total_KDA").toString()

        }
        setCanceledOnTouchOutside(true)

        setContentView(R.layout.user_dialog)

    }
}

