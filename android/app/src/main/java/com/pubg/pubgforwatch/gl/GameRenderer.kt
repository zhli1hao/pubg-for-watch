package com.pubg.pubgforwatch.gl
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.*
import com.pubg.pubgforwatch.GameState
class GameRenderer : GLSurfaceView.Renderer{
    private val proj=FloatArray(16); private val view=FloatArray(16); private val mvp=FloatArray(16)
    private val cube=Cube(); private val ground=Ground()
    private var last=System.currentTimeMillis(); private var circleR=900f
    override fun onSurfaceCreated(g:GL10?,c:EGLConfig?){ GLES20.glClearColor(0.53f,0.81f,0.98f,1f); GLES20.glEnable(GLES20.GL_DEPTH_TEST); cube.build(); ground.build() }
    override fun onSurfaceChanged(g:GL10?,w:Int,h:Int){ GLES20.glViewport(0,0,w,h); Matrix.perspectiveM(proj,0,60f,w.toFloat()/h,1f,2000f) }
    override fun onDrawFrame(g:GL10?){ val now=System.currentTimeMillis(); val dt=min(0.05f,(now-last)/1000f); last=now; update(dt); GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        Matrix.setLookAtM(view,0,GameState.me.x,GameState.me.y+8f,GameState.me.z+0.1f,GameState.me.x+sin(GameState.me.yaw*PI.toFloat()/180f),GameState.me.y+4f,GameState.me.z+cos(GameState.me.yaw*PI.toFloat()/180f),0f,1f,0f)
        Matrix.multiplyMM(mvp,0,proj,0,view,0); ground.draw(mvp,GameState.mapId)
        cube.draw(mvp,GameState.me.x,GameState.me.y,GameState.me.z,0.9f,floatArrayOf(0.2f,0.6f,0.85f))
        GameState.others.forEach{ if(!it.dead)cube.draw(mvp,it.x,it.y,it.z,0.7f,floatArrayOf(0.75f,0.2f,0.15f)) }
    }
    private fun update(dt:Float){ if(GameState.me.y<0.01f){GameState.me.y=0f;return}; GameState.me.y-=40f*dt; if(GameState.me.y<0f)GameState.me.y=0f; circleR-=6f*dt; if(circleR<180f)circleR=180f }
}
