package com.pubg.pubgforwatch.gl
import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
class Cube{ private var vbo=0; private var ibo=0; private var prog=0; private var pos=0; private var mvpH=0; private var colH=0
    private val vs="uniform mat4 uMVP;attribute vec4 vPos;void main(){gl_Position=uMVP*vPos;}"
    private val fs="precision mediump float;uniform vec3 uCol;void main(){gl_FragColor=vec4(uCol,1.0);}"
    fun build(){ val verts=FloatArray(24); val idx=shortArrayOf(0,1,2,2,3,0,1,5,6,6,2,1,5,4,7,7,6,5,4,0,3,3,7,4,3,2,6,6,7,3,0,4,5,5,1,0)
        val ps=arrayOf(-1f,-1f,1f,1f,-1f,1f,1f,1f,1f,-1f,1f,1f,-1f,-1f,-1f,1f,-1f,-1f,1f,1f,-1f,-1f,1f,-1f)
        for(i in 0 until 24)verts[i]=ps[i]*0.5f
        val vb=ByteBuffer.allocateDirect(verts.size*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(verts).apply{position(0)}
        val ib=ByteBuffer.allocateDirect(idx.size*2).order(ByteOrder.nativeOrder()).asShortBuffer().put(idx).apply{position(0)}
        val a=IntArray(1);GLES20.glGenBuffers(1,a);vbo=a[0];GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vbo);GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,verts.size*4,vb,GLES20.GL_STATIC_DRAW)
        GLES20.glGenBuffers(1,a);ibo=a[0];GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,ibo);GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,idx.size*2,ib,GLES20.GL_STATIC_DRAW)
        val vc=GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);GLES20.glShaderSource(vc,vs);GLES20.glCompileShader(vc)
        val fc=GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);GLES20.glShaderSource(fc,fs);GLES20.glCompileShader(fc)
        prog=GLES20.glCreateProgram();GLES20.glAttachShader(prog,vc);GLES20.glAttachShader(prog,fc);GLES20.glLinkProgram(prog)
        pos=GLES20.glGetAttribLocation(prog,"vPos");mvpH=GLES20.glGetUniformLocation(prog,"uMVP");colH=GLES20.glGetUniformLocation(prog,"uCol")
    }
    fun draw(mvp:FloatArray,cx:Float,cy:Float,cz:Float,scale:Float,color:FloatArray){ GLES20.glUseProgram(prog);val m=FloatArray(16);Matrix.setIdentityM(m,0);Matrix.translateM(m,0,cx,cy,cz);Matrix.scaleM(m,0,scale,scale,scale);val mm=FloatArray(16);Matrix.multiplyMM(mm,0,mvp,0,m,0);GLES20.glUniformMatrix4fv(mvpH,1,false,mm,0);GLES20.glUniform3fv(colH,1,color,0);GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vbo);GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,ibo);GLES20.glEnableVertexAttribArray(pos);GLES20.glVertexAttribPointer(pos,3,GLES20.GL_FLOAT,false,0,0);GLES20.glDrawElements(GLES20.GL_TRIANGLES,36,GLES20.GL_UNSIGNED_SHORT,0) }
}
