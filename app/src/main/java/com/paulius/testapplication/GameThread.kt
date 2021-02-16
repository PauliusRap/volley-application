package com.paulius.testapplication

import android.os.SystemClock

class GameThread(private val surface: GameSurface) : Thread() {
    private var running = false
    fun setRunning(running: Boolean) {
        this.running = running
    }

    override fun run() {
        var time1 = System.currentTimeMillis()
        var time2: Long
        while (running) {
            SystemClock.sleep(32)
            time2 = System.currentTimeMillis()
            val delta = (time2 - time1).toInt()
            surface.update(delta)
            time1 = time2
        }
    }

}