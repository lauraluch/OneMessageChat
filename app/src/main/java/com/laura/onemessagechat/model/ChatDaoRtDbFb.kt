package com.laura.onemessagechat.model

import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class ChatDaoRtDbFb: ChatDao {
    companion object {
        private const val CHAT_LIST_ROOT_NODE = "chatList"
    }

    private val chatRtDbFbReference = Firebase.database
        .getReference(CHAT_LIST_ROOT_NODE)

    private val chatList: MutableList<Chat> = mutableListOf()

    init {
        chatRtDbFbReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chat: Chat? = snapshot.getValue<Chat>()

                chat?.also { newChat ->
                    if (!chatList.any{ it.id == newChat.id }){
                        chatList.add(newChat)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chat: Chat? = snapshot.getValue<Chat>()

                chat?.also { editedChat ->
                    chatList.indexOfFirst { editedChat.id == it.id }.also {
                        chatList[it] = editedChat
                    }

                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val chat: Chat? = snapshot.getValue<Chat>()

                chat?.also {
                    chatList.remove(it)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // NSA
            }

            override fun onCancelled(error: DatabaseError) {
                // NSA
            }
        })

        chatRtDbFbReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatMap = snapshot.getValue<Map<String, Chat>>()

                chatList.clear()
                chatMap?.values?.also {
                    chatList.addAll(it)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // NSA
            }
        })
    }

    override fun createChat(chat: Chat): Int {
        createOrUpdateChat(chat)
        return 1
    }

    override fun readChat(id: String): Chat? {
        val index = chatList.indexOfFirst { it.id == id }
        return if (index != -1) {
            chatList[index]
        } else {
            null
        }
    }


    override fun readAllChats(): MutableList<Chat> = chatList

    override fun updateChat(chat: Chat): Int {
        createOrUpdateChat(chat)
        return 1
    }

    private fun createOrUpdateChat(chat: Chat) =
        chatRtDbFbReference.child(chat.id.toString()).setValue(chat)
}