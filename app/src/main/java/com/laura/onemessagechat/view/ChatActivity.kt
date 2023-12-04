package com.laura.onemessagechat.view

import android.content.Intent
import android.os.Bundle
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
                    messageEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
                val generatedId = _receivedChat.id ?: generateId()
                idTv.text = generatedId.toString()
//                idTv.setText(_receivedChat.id.toString())
                messageEt.setText(_receivedChat.message)

            }


        }

        with(cab) {
            saveBt.setOnClickListener {
                val chat = Chat(
                    id = idTv.text.toString().toInt(),
                    message = messageEt.text.toString()
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_CHAT, chat)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun generateId() = Random(System.currentTimeMillis()).nextInt()
}