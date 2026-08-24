package com.pubg.pubgforwatch
data class MatchConfig(val label:String,val teamA:Int,val teamB:Int)
object MatchConfigs{ fun get(m:String):MatchConfig=when(m){"1v1"->MatchConfig("1v1",1,1);"2v2"->MatchConfig("2v2",2,2);"2v1"->MatchConfig("2v1",2,1);"3v3"->MatchConfig("3v3",3,3);else->MatchConfig("Solo",1,0)} }
