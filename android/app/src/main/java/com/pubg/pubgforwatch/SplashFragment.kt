package com.pubg.pubgforwatch
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
class SplashFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_splash, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ObjectAnimator.ofFloat(view.findViewById(R.id.tv_starting), "alpha", 0.3f, 1f).apply { duration=800; repeatMode=ObjectAnimator.REVERSE; repeatCount=ObjectAnimator.INFINITE; start() }
        CoroutineScope(Dispatchers.IO).launch {
            AccountStore(requireContext()); DeviceInfo.deviceId(requireContext())
            delay(1200)
            withContext(Dispatchers.Main) {
                (activity as? MainActivity)?.let { main ->
                    if (AccountStore(requireContext()).hasValidSession()) main.openLobby() else main.openLogin()
                }
            }
        }
    }
}
