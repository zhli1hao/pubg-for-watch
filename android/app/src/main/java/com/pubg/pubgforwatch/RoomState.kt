package com.pubg.pubgforwatch
data class RoomSlot(val nick:String,val team:Int,val isBot:Boolean=false)
class RoomState{ val slots=mutableListOf<RoomSlot>(); var mapId=0; var mode="solo"
    fun join(nick:String,team:Int=0):Boolean{ if(slots.size>=20)return false; slots.add(RoomSlot(nick,team)); return true }
    fun fillBots(){ while(slots.size<20) slots.add(RoomSlot("Bot${slots.size+1}",0,true)) }
    fun serialize():String{ return "$mode|$mapId|"+slots.joinToString(","){"${it.nick}:${it.team}:${if(it.isBot)1 else 0}"} }
}
