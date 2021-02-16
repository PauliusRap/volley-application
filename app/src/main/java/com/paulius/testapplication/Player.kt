package com.paulius.testapplication

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.Drawable

class Player : Drawable() {

    private var coordinateX = 0

    @SuppressLint("CanvasSize")
    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.BLACK
        val height = 300
        val width = 100
        val rect = Rect(
            coordinateX,
            canvas.height - height,
            coordinateX + width,
            canvas.height
        )
        canvas.drawRect(rect, paint)
    }

    fun moveLeft() {
        coordinateX -= 3
    }
    fun moveRight() {
        coordinateX += 3
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