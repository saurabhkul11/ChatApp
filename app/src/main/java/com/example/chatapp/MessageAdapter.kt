package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context,val msglist:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val item_send=2
    val item_recv=1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {

        if (viewType==1){
            val view:View= LayoutInflater.from(context).inflate(R.layout.msg_receive,parent,false)
            return ReceiveViewHolder(view)
        }
        else{
            val view:View= LayoutInflater.from(context).inflate(R.layout.msg_send,parent,false)
            return SendViewHolder(view)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val curr=msglist[position]

        if (holder.javaClass==SendViewHolder::class.java){
            val viewHolder=holder as SendViewHolder
            holder.sentmsg.text=curr.message
        }
        else{
            val viewHolder=holder as ReceiveViewHolder
            holder.recvmsg.text=curr.message
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currmsg=msglist[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currmsg.senderid)){
            return item_send
        }
        else{
            return item_recv
        }

    }

    override fun getItemCount(): Int {
        return msglist.size
    }

    inner class SendViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val sentmsg=itemView.findViewById<TextView>(R.id.txt_send_msg)
    }
    inner class ReceiveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val recvmsg=itemView.findViewById<TextView>(R.id.txt_recv_msg)
    }
}