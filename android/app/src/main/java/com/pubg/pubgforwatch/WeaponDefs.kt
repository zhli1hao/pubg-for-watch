package com.pubg.pubgforwatch
data class Weapon(val id:String,val name:String,val dmg:Float,val cd:Float,val range:Float)
data class Attachment(val id:String,val name:String,val kind:String)
object WeaponDefs{
    val weapons=listOf(
        Weapon("mk14","MK14 EBR",55f,0.12f,700f),
        Weapon("gl","榴弹炮 M79",90f,1.2f,450f),
        Weapon("m416","M416",31f,0.09f,650f),
        Weapon("akm","AKM",38f,0.11f,600f),
        Weapon("scar","SCAR-L",33f,0.10f,620f),
        Weapon("ump","UMP45",27f,0.10f,500f),
        Weapon("dp28","DP-28",36f,0.16f,630f)
    )
    val attachments=listOf(Attachment("scope2","2x Scope","scope"),Attachment("grip","Vertical Grip","grip"),Attachment("mag","Extended Mag","mag"))
}
