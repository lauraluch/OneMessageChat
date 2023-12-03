package com.laura.onemessagechat.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Chat::class], version = 1)
abstract class ChatRoomDaoDatabase: RoomDatabase(){
    abstract fun getContactRoomDao(): ChatRoomDao
}