package com.laura.onemessagechat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.laura.onemessagechat.R
import com.laura.onemessagechat.adapter.ChatAdapter
import com.laura.onemessagechat.databinding.ActivityMainBinding
import com.laura.onemessagechat.model.Chat
import com.laura.onemessagechat.model.Constants.CHAT_ARRAY

class MainActivity : AppCompatActivity() {
    //ViewBinding
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data Source
    private val chatList: MutableList<Chat> = mutableListOf()

    private val chatAdapter: ChatAdapter by lazy {
        ChatAdapter(
            this,
            chatList
        )
    }
    val updateChatListHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            msg.data.getParcelableArray(CHAT_ARRAY)?.also { chatArray ->

                chatList.clear()
                chatArray.forEach {
                    chatList.add(it as Chat)
                }

                chatAdapter.notifyDataSetChanged()
            }
        }
    }

}