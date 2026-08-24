package com.pubg.pubgforwatch
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
class ChatFragment : Fragment(){
    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,savedInstanceState:Bundle?):View=inflater.inflate(R.layout.fragment_chat,container,false)
    override fun onViewCreated(v:View,s:Bundle?){ super.onViewCreated(v,s)
        v.findViewById<Button>(R.id.btnSend).setOnClickListener{ val m=v.findViewById<EditText>(R.id.inputMsg).text.toString(); LanMatchService.send("CHAT $m"); v.findViewById<android.widget.TextView>(R.id.tvChat).append("\n$m") }
        v.findViewById<Button>(R.id.btnVoice).setOnClickListener{ VoiceChatService.startReceive() }
    }
}
