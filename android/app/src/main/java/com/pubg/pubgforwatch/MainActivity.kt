package com.pubg.pubgforwatch
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
class MainActivity : AppCompatActivity() {
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(R.layout.activity_main)
        if (s == null) supportFragmentManager.beginTransaction().replace(R.id.container, SplashFragment()).commit()
    }
    fun openLogin() = switchFrag(LoginFragment())
    fun openLobby() = switchFrag(LobbyFragment())
    fun openMatch() = switchFrag(MatchFragment())
    fun openFriends() = switchFrag(FriendsFragment())
    fun openChat() = switchFrag(ChatFragment())
    fun openGame() = switchFrag(GameFragment())
    fun openSettings() = switchFrag(SettingsFragment())
    private fun switchFrag(f: Fragment) = supportFragmentManager.beginTransaction().replace(R.id.container, f).addToBackStack(null).commit()
}
