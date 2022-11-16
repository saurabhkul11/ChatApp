package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var re:RecyclerView
    private lateinit var adp: UserAdapter
    private lateinit var userlist:ArrayList<User>
    private lateinit var mauth:FirebaseAuth
    private lateinit var mrf:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mauth=FirebaseAuth.getInstance()
        mrf=FirebaseDatabase.getInstance().getReference()


        re=findViewById(R.id.recv)
        userlist=ArrayList()
        adp=UserAdapter(this,userlist)

        re.layoutManager=LinearLayoutManager(this)
        re.adapter=adp

        mrf.child("user").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userlist.clear()
                for (postsnapshot in snapshot.children){
                    val curr=postsnapshot.getValue(User::class.java)
                    if (mauth.currentUser?.uid!=curr?.uid){
                        userlist.add(curr!!)
                    }


                }

                adp.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        } )



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.logout){
            mauth.signOut()
            val intent=Intent(this,LoginActivity::class.java)
            finish()
            startActivity(intent)

            return true
        }
        return true
    }
}