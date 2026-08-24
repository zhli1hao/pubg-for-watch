package com.pubg.pubgforwatch
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
class GameFragment : Fragment(){
    private lateinit var gl:GLSurfaceView
    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,savedInstanceState:Bundle?):View{ gl=GLSurfaceView(requireContext()); gl.setEGLContextClientVersion(2); gl.setRenderer(gl.GameRenderer()); return gl }
    override fun onPause(){ super.onPause(); gl.onPause() }
    override fun onResume(){ super.onResume(); gl.onResume() }
}
