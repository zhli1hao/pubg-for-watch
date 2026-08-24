package com.pubg.pubgforwatch
import android.content.Context
import android.os.Build
import android.provider.Settings
object DeviceInfo{
    fun deviceName()= "${Build.MANUFACTURER} ${Build.MODEL}"
    fun deviceId(c:Context)=Settings.Secure.getString(c.contentResolver,Settings.Secure.ANDROID_ID)?:"unknown"
    fun serial():String? = if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.O_MR1) Build.getSerial() else null
}
