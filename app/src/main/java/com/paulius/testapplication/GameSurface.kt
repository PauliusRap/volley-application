package com.paulius.testapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

private const val DEBUG = false

class GameSurface(context: Context?, attrs: AttributeSet?) :
    SurfaceView(context, attrs), SurfaceHolder.Callback {

    private var thread: GameThread? = null
     val ball = Volleyball(resources)
    private val net = Net()
    val player = Player()
    private var maxX = 0
    private var maxY = 0

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        ball.draw(canvas)
        net.draw(canvas)
        player.draw(canvas)
    }

    fun update(delta: Int) {

        ball.update(delta, maxX, maxY)

        postInvalidateOnAnimation()
        if (DEBUG) {
            Log.e("Ball: ", ball.x.toString())
            Log.e("Ball: ", ball.y.toString())
        }
    }

    fun setThreadRunning(running: Boolean) {
        thread?.setRunning(running)
    }

    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int, width: Int,
        height: Int
    ) {
        maxX = width
        maxY = height
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        maxX = width
        maxY = height
        thread = GameThread(this)
        thread!!.setRunning(true)
        thread!!.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        thread?.setRunning(false)
        while (retry) {
            try {
                thread?.join()
                retry = false
            } catch (e: InterruptedException) {
            }
        }
    }

    init {
        holder.addCallback(this)
    }
}