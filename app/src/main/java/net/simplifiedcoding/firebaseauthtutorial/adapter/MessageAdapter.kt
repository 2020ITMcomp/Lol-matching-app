package net.simplifiedcoding.firebaseauthtutorial.adapter

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.xwray.groupie.databinding.BindableItem
import net.simplifiedcoding.firebaseauthtutorial.R
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

class ReceiveMessageItem(private val message: Message, val context: Context?,val nickname:String) : BindableItem<ItemMessageReceivedBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_message_received
    }

    override fun bind(viewBinding: ItemMessageReceivedBinding, position: Int) {
        viewBinding.textMessageBody.text = message.text_message_body
        viewBinding.textMessageName.text = message.text_message_name
        viewBinding.textMessageTime.text = SimpleDateFormat("HH:mm").format(message.timeStamp.toLong())
        viewBinding.imageMessageProfile.setOnClickListener {view ->


            if (context != null) {

                CDialog(context,nickname = nickname).show()

            }

        }
    }

}

class CDialog

    constructor(context: Context,nickname: String) : Dialog(context){



    init {

        getSummonerInfoRef(nickname).get().addOnSuccessListener { info ->
            SummonerInfo(
                nickname = info.get("name").toString(),
                level = info.get("summonerLevel") as Long,
                typeB = info.get("B_Feature").toString(),
                typeJ = info.get("J_Feature").toString(),
                typeM = info.get("M_Feature").toString(),
                typeT = info.get("T_Feature").toString(),
                winrateB = info.get("B_Win") as Double,
                winrateJ = info.get("J_Win") as Double,
                winrateM = info.get("M_Win") as Double,
                winrateT = info.get("T_Win") as Double,
                KDA_B = info.get("B_KDA") as Double,
                KDA_J = info.get("J_KDA") as Double,
                KDA_M = info.get("M_KDA") as Double,
                KDA_T = info.get("T_KDA") as Double,
                KDA_Total = info.get("T_KDA") as Double,
                winrateTotal = info.get("T_Win") as Double

            )
            findViewById<TextView>(R.id.name).text = info.get("name").toString()
            findViewById<TextView>(R.id.level).text = info.get("summonerLevel").toString()
            findViewById<TextView>(R.id.bottomkda).text = info.get("B_KDA").toString()
            findViewById<TextView>(R.id.topkda).text = info.get("T_KDA").toString()
            findViewById<TextView>(R.id.junglekda).text = info.get("J_KDA").toString()
            findViewById<TextView>(R.id.middlekda).text = info.get("M_KDA").toString()
            findViewById<TextView>(R.id.topwinrate).text = info.get("T_Win").toString()
            findViewById<TextView>(R.id.junglewinrate).text = info.get("J_Win").toString()
            findViewById<TextView>(R.id.middlewinrate).text = info.get("M_Win").toString()
            findViewById<TextView>(R.id.bottomwinrate).text = info.get("B_Win").toString()
            findViewById<TextView>(R.id.topfeature).text = info.get("T_Feature").toString()
            findViewById<TextView>(R.id.junglefeature).text = info.get("J_Feature").toString()
            findViewById<TextView>(R.id.middlefeature).text = info.get("M_Feature").toString()
            findViewById<TextView>(R.id.bottomfeature).text = info.get("B_Feature").toString()
            findViewById<TextView>(R.id.totalKDA).text = info.get("Total_KDA").toString()
            findViewById<TextView>(R.id.totalWinRate).text = info.get("Total_Win").toString()
        }
        setCanceledOnTouchOutside(true)

        setContentView(R.layout.user_dialog)

    }
}

