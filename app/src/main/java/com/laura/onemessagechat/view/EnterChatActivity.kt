package com.laura.onemessagechat.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.laura.onemessagechat.controller.ChatRoomController
import com.laura.onemessagechat.databinding.EnterChatActivityBinding

class EnterChatActivity  : AppCompatActivity() {
    private val ecab: EnterChatActivityBinding by lazy {
        EnterChatActivityBinding.inflate(layoutInflater)
    }
//
//    private val chatRoomController = ChatRoomController(this)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(ecab.root)
//
//        setSupportActionBar(ecab.toolbarIn.toolbar)
//        supportActionBar?.subtitle = "Enter chat"
//
//        ecab.btnEnviar.setOnClickListener {
//            val chatIdText = ecab.enterChatEt.text.toString().trim()
//
//            if (chatIdText.isNotEmpty()) {
//                val chatId = chatIdText.toInt()
//
//                // Verifica se o chat com o ID fornecido existe
//                val chat = chatRoomController.getChat(chatId)
//
//                if (chat != null) {
//                    // Se o chat for encontrado, imprime "achou" no console
//                    println("Achou: $chat")
//                } else {
//                    // Se o chat não for encontrado, imprime "não achou" no console
//                    println("Não achou")
//                }
//            } else {
//                // Caso o campo de ID esteja vazio
//                Toast.makeText(this, "Digite o código do chat", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//


}