package com.pubg.pubgforwatch
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
class LobbyFragment : Fragment(){
    private val maps=listOf("平原","Erangel-lite","沙漠"); private var mi=0; private val modes=listOf("Solo","1v1","2v2","2v1","3v3"); private var mdi=0
    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,savedInstanceState:Bundle?):View=inflater.inflate(R.layout.fragment_lobby,container,false)
    override fun onViewCreated(v:View,s:Bundle?){ super.onViewCreated(v,s)
        fun refresh(){ v.findViewById<android.widget.TextView>(R.id.tv_map).text="地图：${maps[mi]}（点击切换）"; v.findViewById<android.widget.TextView>(R.id.tv_mode).text="模式：${modes[mdi]}（点击切换）" }
        v.findViewById<android.widget.TextView>(R.id.tv_map).setOnClickListener{ mi=(mi+1)%maps.size; GameState.mapId=mi; refresh() }
        v.findViewById<android.widget.TextView>(R.id.tv_mode).setOnClickListener{ mdi=(mdi+1)%modes.size; GameState.mode=modes[mdi]; refresh() }
        refresh()
        v.findViewById<Button>(R.id.btnStart).setOnClickListener{ GameState.isLan=false; (activity as MainActivity).openGame() }
        v.findViewById<Button>(R.id.btnCreate).setOnClickListener{ GameState.isLan=true; GameState.isHost=true; (activity as MainActivity).openMatch() }
        v.findViewById<Button>(R.id.btnJoin).setOnClickListener{ GameState.isLan=true; GameState.isHost=false; (activity as MainActivity).openMatch() }
        v.findViewById<Button>(R.id.btnFriends).setOnClickListener{ (activity as MainActivity).openFriends() }
        v.findViewById<Button>(R.id.btnSettings).setOnClickListener{ (activity as MainActivity).openSettings() }
    }
}
