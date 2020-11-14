package net.simplifiedcoding.firebaseauthtutorial.adapter

import android.view.View
import androidx.viewbinding.ViewBinding
import net.simplifiedcoding.firebaseauthtutorial.databinding.ItemMessageReceivedBinding

data class Message(val uid : String, val text_message_body : String, val text_message_name : String, val timeStamp : Long)
// TODO : TimeStamp 추가하기.


