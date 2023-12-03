package com.laura.onemessagechat.controller

import android.os.Message
import androidx.room.Room
import com.laura.onemessagechat.model.Chat
import com.laura.onemessagechat.model.ChatRoomDao
import com.laura.onemessagechat.model.ChatRoomDao.Companion.CHAT_DATABASE_FILE
import com.laura.onemessagechat.model.ChatRoomDaoDatabase
import com.laura.onemessagechat.model.Constants.CHAT_ARRAY
import com.laura.onemessagechat.view.MainActivity


class ChatRoomController(private val mainActivity: MainActivity) {
    private val chatDaoImpl: ChatRoomDao by lazy {
        Room.databaseBuilder(
            mainActivity,
            ChatRoomDaoDatabase::class.java,
            CHAT_DATABASE_FILE
        ).build().getContactRoomDao()
    }

    fun insertChat(chat: Chat) {
        Thread {
            chatDaoImpl.createChat(chat)
            getAllChats()
        }.start()
    }

    fun getChat(id: Int) = chatDaoImpl.readChat(id)

    fun getAllChats() {
        Thread {
            val returnList = chatDaoImpl.readAllChats()

            mainActivity.updateChatListHandler.apply {
                sendMessage(Message().apply {
                    data.putParcelableArray(
                        CHAT_ARRAY,
                        returnList.toTypedArray()
                    )
                })
            }

        }.start()
    }

    fun editChat(chat: Chat) {
        Thread {
            chatDaoImpl.updateChat(chat)
            getAllChats()
        }.start()
    }
}