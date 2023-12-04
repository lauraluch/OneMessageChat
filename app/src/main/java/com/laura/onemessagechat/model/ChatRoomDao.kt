package com.laura.onemessagechat.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ChatRoomDao {

    companion object {
        const val CHAT_DATABASE_FILE = "chats_room"
        private const val CHAT_TABLE = "chat"
        private const val ID_COLUMN = "id"
        private const val MESSAGE_COLUMN = "message"

    }

    @Insert
    fun createChat(chat: Chat)

    @Query("SELECT *FROM $CHAT_TABLE WHERE $ID_COLUMN = :id")
    fun readChat(id: String): Chat?
    
    @Query("SELECT * FROM $CHAT_TABLE ORDER BY $MESSAGE_COLUMN")
    fun readAllChats(): MutableList<Chat>

    @Update
    fun updateChat(contact: Chat): Int

}