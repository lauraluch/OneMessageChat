package com.laura.onemessagechat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.laura.onemessagechat.R
import com.laura.onemessagechat.adapter.ChatAdapter
import com.laura.onemessagechat.databinding.ActivityMainBinding
import com.laura.onemessagechat.model.Chat
import com.laura.onemessagechat.model.Constants.CHAT_ARRAY
import com.laura.onemessagechat.model.Constants.EXTRA_CHAT
import com.laura.onemessagechat.model.Constants.VIEW_CHAT
import kotlinx.android.parcel.Parcelize

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

    companion object {
        const val GET_CHATS_MSG = 1
        const val GET_CHATS_INTERVAL = 2000L
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

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)
        amb.chatsLv.adapter=chatAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result ->
            if (result.resultCode == RESULT_OK){
                val chat = result.data?.getParcelableExtra<Chat>(EXTRA_CHAT)
                chat?.let { _chat ->
                    if(chatList.any { it.id == chat.id }){
//                        chatController.editChat(_contact)
                    }else {
//                        chatController.insertChat(_chat)
                    }
                }
            }
        }

        amb.chatsLv.setOnItemClickListener{ parent, view, position, id->
            val chat = chatList[position]
            val viewChatIntent = Intent(this, ChatActivity::class.java)
                .putExtra(EXTRA_CHAT, chat)
                .putExtra(VIEW_CHAT,true)

            startActivity(viewChatIntent)
        }

        registerForContextMenu(amb.chatsLv)
        updateChatListHandler.apply {
            sendMessageDelayed(
                obtainMessage().apply { what = GET_CHATS_MSG },
                GET_CHATS_INTERVAL
            )
        }
    }

}