package com.laura.onemessagechat.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.laura.onemessagechat.R
import com.laura.onemessagechat.model.Chat
import com.laura.onemessagechat.databinding.TileChatBinding

class ChatAdapter(
    context: Context, private val chatList: MutableList<Chat>):
    ArrayAdapter<Chat>(context, R.layout.tile_chat, chatList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val chat = chatList[position]
        var chatTileView = convertView
        var tcb: TileChatBinding?= null

        if(chatTileView == null) {
            tcb = TileChatBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            chatTileView = tcb.root
            val tileContactHolder = TileChatHolder(tcb.idTv, tcb.messageTv)
            chatTileView.tag = tileContactHolder
        }

        val holder = chatTileView.tag as TileChatHolder
        holder.idTv.setText(chat.id.toString())
        holder.messageTv.setText(chat.message)


        return chatTileView
    }

    private data class TileChatHolder(val idTv: TextView, val messageTv: TextView)
}