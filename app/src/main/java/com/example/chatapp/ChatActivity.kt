package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {
    private lateinit var msg_recv:RecyclerView
    private lateinit var msgbox:TextView
    private lateinit var sendb:ImageView
    private lateinit var adapter: MessageAdapter
    private lateinit var msglist:ArrayList<Message>
    private lateinit var mdb:DatabaseReference


   var recv_room:String?=null
    var send_room:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


       // val intent=Intent()
        val name=intent.getStringExtra("name")
        val recv_uid=intent.getStringExtra("UID")
        val send_uid=FirebaseAuth.getInstance().currentUser?.uid


        mdb=FirebaseDatabase.getInstance().getReference()


        send_room=recv_uid+send_uid
        recv_room=send_uid+recv_uid


        supportActionBar?.title=name

        msgbox=findViewById(R.id.edt)
        sendb=findViewById(R.id.send_button)
        msg_recv=findViewById(R.id.recv_chat)

        msglist= ArrayList()
        adapter= MessageAdapter(this,msglist)



        msg_recv.layoutManager=LinearLayoutManager(this)
        msg_recv.adapter=adapter



        mdb.child("chats").child(send_room!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    msglist.clear()
                    for (postsnapshot in snapshot.children){
                        var msg=postsnapshot.getValue(Message::class.java)
                        msglist.add(msg!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



        sendb.setOnClickListener{
            val message=msgbox.text.toString()
            val mobject=Message(message,send_uid)

            mdb.child("chats").child(send_room!!).child("messages").push()
                .setValue(mobject).addOnSuccessListener {
                    mdb.child("chats").child(recv_room!!).child("messages").push()
                        .setValue(mobject)
                }
            msgbox.setText("")

        }





    }
}