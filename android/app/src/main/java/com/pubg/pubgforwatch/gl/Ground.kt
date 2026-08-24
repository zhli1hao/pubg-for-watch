package com.pubg.pubgforwatch.gl
import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
class Ground{ private var vbo=0; private var ibo=0; private var prog=0; private var pos=0; private var mvpH=0; private var colH=0
    private val vs="uniform mat4 uMVP;attribute vec4 vPos;void main(){gl_Position=uMVP*vPos;}"
    private val fs="precision mediump float;uniform vec3 uCol;void main(){gl_FragColor=vec4(uCol,1.0);}"
    fun build(){ val v=FloatArray(12){-1000f,0f,-1000f,1000f,0f,-1000f,1000f,0f,1000f,-1000f,0f,1000f}; val idx=shortArrayOf(0,1,2,2,3,0)
        val vb=ByteBuffer.allocateDirect(v.size*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(v).apply{position(0)}
        val ib=ByteBuffer.allocateDirect(idx.size*2).order(ByteOrder.nativeOrder()).asShortBuffer().put(idx).apply{position(0)}
        GLES20.glGenBuffers(1,IntArray(1).also{GLES20.glGenBuffers(1,it)});vbo=0;GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vbo)
        val vc=GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);GLES20.glShaderSource(vc,vs);GLES20.glCompileShader(vc)
        val fc=GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);GLES20.glShaderSource(fc,fs);GLES20.glCompileShader(fc)
        prog=GLES20.glCreateProgram();GLES20.glAttachShader(prog,vc);GLES20.glAttachShader(prog,fc);GLES20.glLinkProgram(prog)
        pos=GLES20.glGetAttribLocation(prog,"vPos");mvpH=GLES20.glGetUniformLocation(prog,"uMVP");colH=GLES20.glGetUniformLocation(prog,"uCol")
    }
    fun draw(mvp:FloatArray,mapId:Int){ GLES20.glUseProgram(prog);val col=when(mapId){0->floatArrayOf(0.34f,0.6f,0.28f);1->floatArrayOf(0.4f,0.55f,0.3f);else->floatArrayOf(0.86f,0.78f,0.5f)};GLES20.glUniform3fv(colH,1,col,0);GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vbo);GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,ibo);GLES20.glEnableVertexAttribArray(pos);GLES20.glVertexAttribPointer(pos,3,GLES20.GL_FLOAT,false,0,0);GLES20.glDrawElements(GLES20.GL_TRIANGLES,6,GLES20.GL_UNSIGNED_SHORT,0) }
}
