package com.laura.onemessagechat.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.laura.onemessagechat.databinding.ChatActivityBinding
import com.laura.onemessagechat.model.Chat
import com.laura.onemessagechat.model.Constants.EXTRA_CHAT
import com.laura.onemessagechat.model.Constants.VIEW_CHAT
import kotlin.random.Random

class ChatActivity : AppCompatActivity() {
    private val cab: ChatActivityBinding by lazy {
        ChatActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cab.root)

        setSupportActionBar(cab.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Chat details"

        val receivedChat = intent.getParcelableExtra<Chat>(EXTRA_CHAT)
        receivedChat?.let {_receivedChat ->
            val viewChat: Boolean = intent.getBooleanExtra(VIEW_CHAT, false)
            with(cab) {
                if (viewChat) {
                    idEt.isEnabled = false
                    messageEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
                idEt.isEnabled = false
                idEt.setText(_receivedChat.id)
                messageEt.setText(_receivedChat.message)

            }


        }

        with(cab) {
            saveBt.setOnClickListener {
                val chat = Chat(
                    id = idEt.text.toString(),
                    message = messageEt.text.toString()
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_CHAT, chat)
                setResult(RESULT_OK, resultIntent)

                finish()
            }
        }
    }

}