package com.pubg.pubgforwatch
data class Character(val id:String,val name:String,val male:Boolean)
object Characters{ val list=listOf(Character("m","男",true),Character("f","女",false)) }
