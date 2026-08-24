package com.pubg.pubgforwatch
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
class LoginFragment : Fragment(){
    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,savedInstanceState:Bundle?):View=inflater.inflate(R.layout.fragment_login,container,false)
    override fun onViewCreated(v:View,s:Bundle?){ super.onViewCreated(v,s)
        val acc=AccountStore(requireContext())
        v.findViewById<Button>(R.id.btnFull).setOnClickListener{ val nick=v.findViewById<EditText>(R.id.nick).text.toString(); val pass=v.findViewById<EditText>(R.id.pass).text.toString(); if(acc.register(nick,pass,"full"))(activity as MainActivity).openLobby() else acc.login(nick,pass)?.let{(activity as MainActivity).openLobby()} }
        v.findViewById<Button>(R.id.btnGuest).setOnClickListener{ (activity as MainActivity).openLobby() }
        v.findViewById<Button>(R.id.btnDevice).setOnClickListener{ (activity as MainActivity).openLobby() }
        v.findViewById<Button>(R.id.btnDev).setOnClickListener{ val user=v.findViewById<EditText>(R.id.devUser).text.toString(); val pass=v.findViewById<EditText>(R.id.devPass).text.toString(); if(user=="001"&&pass=="admin123"){ if(!acc.hasDevProfile()){ acc.createDevProfile("dev",pass); (activity as MainActivity).openLobby() } else if(acc.devLogin(pass))(activity as MainActivity).openLobby() } }
    }
}
