package com.pubg.pubgforwatch
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
class MatchFragment : Fragment(){
    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,savedInstanceState:Bundle?):View=inflater.inflate(R.layout.fragment_match,container,false)
    override fun onViewCreated(v:View,s:Bundle?){ super.onViewCreated(v,s)
        val room=RoomState(); room.mapId=GameState.mapId; room.mode=GameState.mode
        v.findViewById<Button>(R.id.btnHost).setOnClickListener{ val name=v.findViewById<EditText>(R.id.roomName).text.toString(); val pwd=v.findViewById<EditText>(R.id.roomPwd).text.toString(); LanMatchService.startHost(); room.join("me",0); room.fillBots(); LanMatchService.send("CREATE $name $pwd"); (activity as MainActivity).openGame() }
        v.findViewById<Button>(R.id.btnQuick).setOnClickListener{ room.join("me",0); room.fillBots(); (activity as MainActivity).openGame() }
        v.findViewById<Button>(R.id.btnDoJoin).setOnClickListener{ val ip=v.findViewById<EditText>(R.id.hostIp).text.toString(); val name=v.findViewById<EditText>(R.id.roomName).text.toString(); val pwd=v.findViewById<EditText>(R.id.roomPwd).text.toString(); LanMatchService.connectHost(ip); LanMatchService.send("JOIN $name $pwd"); (activity as MainActivity).openGame() }
        v.findViewById<Button>(R.id.btnBegin).setOnClickListener{ LanMatchService.send("START"); (activity as MainActivity).openGame() }
    }
}
