package com.pubg.pubgforwatch
import android.content.Context
import android.content.SharedPreferences
import java.security.MessageDigest
import java.util.UUID
import android.provider.Settings
data class Account(val nick:String, val mode:String, val created:Long, val lastLogin:Long, val deviceId:String, val serial:String?)
class AccountStore(ctx:Context){
    private val sp:SharedPreferences = ctx.getSharedPreferences("pubg_acc", Context.MODE_PRIVATE)
    private fun h(s:String)=MessageDigest.getInstance("SHA-256").digest(s.toByteArray()).joinToString(""){"%02x".format(it)}
    fun nextId():Int{ var c=sp.getInt("account_count",0)+1; sp.edit().putInt("account_count",c).apply(); return 1000+c }
    fun register(nick:String, pass:String, mode:String, serial:String?=null):Boolean{
        if(sp.contains("nick_$nick")) return false
        val id=nextId()
        sp.edit().putString("nick_$nick","$mode|${h(pass)}|${System.currentTimeMillis()}|${System.currentTimeMillis()}|${DeviceInfo.deviceId(ctx)}|$serial|$id").apply()
        return true
    }
    fun login(nick:String, pass:String):Account?{
        val v=sp.getString("nick_$nick",null)?:return null
        val p=v.split("|"); if(p.size<7) return null
        if(p[1]!=h(pass)) return null
        if(p[0]=="guest"){ if(System.currentTimeMillis()-p[3].toLong()>30L*24*3600*1000){sp.edit().remove("nick_$nick").apply();return null} }
        sp.edit().putString("nick_$nick","${p[0]}|${p[1]}|${p[2]}|${System.currentTimeMillis()}|${p[4]}|${p[5]}|${p[6]}").apply()
        return Account(nick,p[0],p[2].toLong(),System.currentTimeMillis(),p[4],p[5])
    }
    fun guestLogin(nick:String,pass:String):Account{ return Account(nick,"guest",System.currentTimeMillis(),System.currentTimeMillis(),DeviceInfo.deviceId(ctx),null) }
    fun hasValidSession():Boolean{ return sp.getLong("last_active",0)>0 }
    fun createDevProfile(nick:String,pass:String){ sp.edit().putString("dev_nick",nick).putString("dev_pass",h(pass)).apply() }
    fun hasDevProfile():Boolean=sp.contains("dev_nick")
    fun getDevProfile():Account{ val n=sp.getString("dev_nick","")!!; return Account(n,"dev",0,0,DeviceInfo.deviceId(ctx),null) }
    fun devLogin(pass:String):Boolean= sp.getString("dev_pass",null)==h(pass)
    fun findById(id:Int):String?{ sp.all.keys.filter{it.startsWith("nick_")}.forEach{ k->val p=sp.getString(k,"")!!.split("|");if(p.size>=7&&p[6]==id.toString())return k.removePrefix("nick_")};return null }
    fun findByNick(nick:String):Boolean=sp.contains("nick_$nick")
}
