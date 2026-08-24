package com.pubg.pubgforwatch
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
class FriendsFragment : Fragment(){
    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,savedInstanceState:Bundle?):View=inflater.inflate(R.layout.fragment_friends,container,false)
    override fun onViewCreated(v:View,s:Bundle?){ super.onViewCreated(v,s)
        val acc=AccountStore(requireContext())
        v.findViewById<Button>(R.id.btnSearch).setOnClickListener{ val key=v.findViewById<EditText>(R.id.searchKey).text.toString().trim(); val result=if(key.toIntOrNull()!=null) acc.findById(key.toIntOrNull()!!) else if(acc.findByNick(key)) key else null; v.findViewById<android.widget.TextView>(R.id.tvResult).text=if(result!=null)"找到：$result（ID ${acc.nextId()-1}）" else "未找到"; if(result!=null)FriendStore.add(requireContext(),"me",result) }
    }
}
