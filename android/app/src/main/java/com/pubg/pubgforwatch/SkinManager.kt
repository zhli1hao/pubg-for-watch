package com.pubg.pubgforwatch
import android.net.Uri
object SkinManager{ private val skins=mutableMapOf<String,Uri>()
    fun importSkin(charId:String,uri:Uri){ skins[charId]=uri }
    fun getSkin(charId:String):Uri?=skins[charId]
}
