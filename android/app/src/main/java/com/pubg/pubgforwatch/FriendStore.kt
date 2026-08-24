package com.pubg.pubgforwatch
import android.content.Context
import org.json.JSONArray
object FriendStore{
    private const val KEY="friends_%s"
    fun list(ctx:Context,nick:String):List<String>{ val s=ctx.getSharedPreferences("pubg_friends",Context.MODE_PRIVATE).getString(KEY.format(nick),null); return if(s.isNullOrEmpty()) emptyList() else JSONArray(s).let{ a->(0 until a.length()).map{a.getString(it)} } }
    fun add(ctx:Context,nick:String,other:String){ val l=list(ctx,nick).toMutableList(); if(!l.contains(other))l.add(other); ctx.getSharedPreferences("pubg_friends",Context.MODE_PRIVATE).edit().putString(KEY.format(nick),JSONArray(l).toString()).apply() }
    fun remove(ctx:Context,nick:String,other:String){ val l=list(ctx,nick).toMutableList();l.remove(other);ctx.getSharedPreferences("pubg_friends",Context.MODE_PRIVATE).edit().putString(KEY.format(nick),JSONArray(l).toString()).apply() }
}
