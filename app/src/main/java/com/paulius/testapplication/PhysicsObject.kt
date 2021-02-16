package com.paulius.testapplication

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

abstract class PhysicsObject : Drawable(){
    open var x = 100f
    open var y = 100f
    open var velocityX = 0f
    open var velocityY = 0f
    val weight = 0
    private val resource: Int = 0

    override fun draw(canvas: Canvas) {
    }

    open fun update(timeDelta: Int, maxX: Int, maxY: Int) {
    }

    override fun setAlpha(alpha: Int) {
//        TODO("Not yet implemented")
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
//        TODO("Not yet implemented")
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }
}