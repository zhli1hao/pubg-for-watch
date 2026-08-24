package com.pubg.pubgforwatch
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
object VoiceChatService{
    private const val PORT=5051; private const val SAMPLE=8000
    fun startBroadcast(targetIp:String){ Thread{ try{ val sock=DatagramSocket(); val addr=InetAddress.getByName(targetIp); val rec=AudioRecord(MediaRecorder.AudioSource.MIC,SAMPLE,android.media.AudioFormat.CHANNEL_IN_MONO,android.media.AudioFormat.ENCODING_PCM_16BIT,1024); rec.startRecording(); val buf=ByteArray(1024); while(true){ val n=rec.read(buf,0,buf.size); if(n>0){sock.send(DatagramPacket(buf.copyOf(n),n,addr,PORT))} } }catch(_:Exception){} }.start() }
    fun startReceive(){ Thread{ try{ val sock=DatagramSocket(PORT); val track=AudioTrack.Builder().setAudioFormat(android.media.AudioFormat.Builder().setEncoding(android.media.AudioFormat.ENCODING_PCM_16BIT).setSampleRate(SAMPLE).setChannelMask(android.media.AudioFormat.CHANNEL_OUT_MONO).build()).setBufferSizeInBytes(2048).build(); track.play(); val buf=ByteArray(1024); while(true){ val p=DatagramPacket(buf,buf.size); sock.receive(p); track.write(p.data,0,p.length) } }catch(_:Exception){} }.start() }
}
