package com.laura.onemessagechat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.laura.onemessagechat.R
import com.laura.onemessagechat.adapter.ChatAdapter
import com.laura.onemessagechat.controller.ChatRoomController
import com.laura.onemessagechat.controller.ChatRtDbFbController
import com.laura.onemessagechat.databinding.ActivityMainBinding
import com.laura.onemessagechat.model.Chat
import com.laura.onemessagechat.model.Constants.CHAT_ARRAY
import com.laura.onemessagechat.model.Constants.EXTRA_CHAT
import com.laura.onemessagechat.model.Constants.VIEW_CHAT
import kotlinx.android.parcel.Parcelize
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    //ViewBinding
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data Source
    private val chatList: MutableList<Chat> = mutableListOf()
    private val chatsYouParticipate: MutableList<Chat> = mutableListOf()

    private val chatAdapter: ChatAdapter by lazy {
        ChatAdapter(
            this,
            chatsYouParticipate
        )
    }

    private val chatController: ChatRtDbFbController by lazy {
        ChatRtDbFbController(this)
    }

    companion object {
        const val GET_CHATS_MSG = 1
        const val GET_CHATS_INTERVAL = 2000L
    }

    val updateChatListHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            msg.data.getParcelableArray(CHAT_ARRAY)?.also { chatArray ->
                chatsYouParticipate.clear()
                chatArray.forEach {
                    chatsYouParticipate.add(it as Chat)
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
                    if(chatsYouParticipate.any { it.id == chat.id }){
                        chatController.editChat(_chat)
                    }else {
                        chatController.insertChat(_chat)
                    }
                }
            }
        }

        amb.chatsLv.setOnItemClickListener{ parent, view, position, id->
            val chatToEdit = chatsYouParticipate[position]
            val editChatIntent = Intent(this, ChatActivity::class.java)
            editChatIntent.putExtra(EXTRA_CHAT, chatToEdit)
            carl.launch(editChatIntent)
            true
        }

        registerForContextMenu(amb.chatsLv)
        updateChatListHandler.apply {
            sendMessageDelayed(
                obtainMessage().apply { what = GET_CHATS_MSG },
                GET_CHATS_INTERVAL
            )
        }

        amb.enterChatBt.setOnClickListener {
            val chatIdText = amb.enterChatEt.text.toString().trim()

            if (chatIdText.isNotEmpty()) {
                try {
                    chatController.getChat(chatIdText, object : ChatRtDbFbController.OnChatFoundListener {
                        override fun onChatFound(chat: Chat) {
                            if (!chatsYouParticipate.any { it.id == chat.id }) {
                                chatsYouParticipate.add(chat)
                                chatAdapter.notifyDataSetChanged()
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Você já está nessa conversa.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onChatNotFound() {
                            Toast.makeText(
                                this@MainActivity,
                                "Chat não encontrado. Insira outro valor", Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Número muito grande. Insira outro valor", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Digite o código do chat", Toast.LENGTH_SHORT).show()
            }
        }

        chatsYouParticipate
//        chatController.getAllChats()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.createChatMi -> {
                carl.launch(Intent(this,ChatActivity::class.java))
                true
            }
            R.id.exitApp -> {
                finishAffinity()
                true
            }
            else -> true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterForContextMenu(amb.chatsLv)
    }
}