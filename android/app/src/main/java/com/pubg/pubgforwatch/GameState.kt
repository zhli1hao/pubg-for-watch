package com.pubg.pubgforwatch
data class PlayerState(var x:Float,var y:Float,var z:Float,var yaw:Float,var hp:Float=100f,var kills:Int=0,var dead:Boolean=false)
object GameState{ var mode:String="solo"; var mapId:Int=0; var isLan:Boolean=false; var isHost:Boolean=false; val me=PlayerState(0f,0f,0f,0f); val others=mutableListOf<PlayerState>(); var myTeam:Int=0 }
