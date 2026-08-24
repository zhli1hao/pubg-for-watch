package com.pubg.pubgforwatch
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread
object LanMatchService{
    private const val PORT=5050
    var hostSocket:ServerSocket?=null; var client:Socket?=null; var writer:PrintWriter?=null; var reader:BufferedReader?=null
    var onState:(String)->Unit={}; var onShot:(String)->Unit={}; var onKill:(String)->Unit={}; var onChat:(String)->Unit={}
    fun startHost(){ thread{ hostSocket=ServerSocket(PORT); val s=hostSocket!!.accept(); client=s; writer=PrintWriter(s.getOutputStream(),true); reader=s.inputStream.bufferedReader(); loop() } }
    fun connectHost(ip:String){ thread{ val s=Socket(ip,PORT); client=s; writer=PrintWriter(s.getOutputStream(),true); reader=s.inputStream.bufferedReader(); loop() } }
    fun send(line:String){ try{writer?.println(line)}catch(_:Exception){} }
    fun loop(){ try{ while(true){ val l=reader?.readLine()?:break; when{ l.startsWith("STATE ")->onState(l.removePrefix("STATE ")); l.startsWith("SHOT ")->onShot(l.removePrefix("SHOT ")); l.startsWith("KILL ")->onKill(l.removePrefix("KILL ")); l.startsWith("CHAT ")->onChat(l.removePrefix("CHAT ")) } } }catch(_:Exception){} }
    fun stop(){ try{client?.close()}catch(_:Exception){}; try{hostSocket?.close()}catch(_:Exception){} }
}
