package com.pubg.pubgforwatch
import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
object CloudFriendStore{
    private var db:FirebaseDatabase?=null
    fun init(ctx:Context){ try{db=FirebaseDatabase.getInstance()}catch(_:Exception){} }
    fun addFriend(myNick:String,other:String){ db?.getReference("friends/$myNick")?.child(other)?.setValue(true) }
    fun listFriends(myNick:String,cb:(List<String>)->Unit){ db?.getReference("friends/$myNick")?.addListenerForSingleValueEvent(object:ValueEventListener{ override fun onDataChange(s:DataSnapshot){ cb(s.children.map{it.key?:"")});} override fun onCancelled(e:DatabaseError){cb(emptyList())} }) }
    fun registerUser(nick:String,id:Int){ db?.getReference("users/$nick")?.setValue(mapOf("id" to id,"nick" to nick)) }
}
