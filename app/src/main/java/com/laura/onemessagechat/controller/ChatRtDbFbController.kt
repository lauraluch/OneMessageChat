package com.laura.onemessagechat.controller

import android.os.Message
import com.laura.onemessagechat.model.Chat
import com.laura.onemessagechat.model.ChatDao
import com.laura.onemessagechat.model.ChatDaoRtDbFb
import com.laura.onemessagechat.model.Constants
import com.laura.onemessagechat.view.MainActivity

class ChatRtDbFbController(private val mainActivity: MainActivity) {
    private val chatDaoImpl: ChatDao = ChatDaoRtDbFb()

    fun insertChat(chat: Chat) {
        Thread {
            chatDaoImpl.createChat(chat)
        }.start()
    }

    fun getChat(id: String) = chatDaoImpl.readChat(id)

    fun getChats() {
        Thread {
            val returnList = chatDaoImpl.readAllChats()

            mainActivity.updateChatListHandler.apply {
                sendMessage(Message().apply {
                    data.putParcelableArray(
                        Constants.CHAT_ARRAY,
                        returnList.toTypedArray()
                    )
                })
            }

        }.start()
    }

    fun editChat(chat: Chat){
        Thread {
            chatDaoImpl.updateChat(chat)
        }.start()
    }

}