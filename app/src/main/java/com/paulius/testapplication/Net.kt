package com.paulius.testapplication

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.Drawable


class Net : Drawable() {

    @SuppressLint("CanvasSize")
    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.BLACK
        val height = canvas.height / 2.5
        val width = 30
        val rect = Rect(
            canvas.width / 2 - width / 2,
            (canvas.height - height).toInt(),
            (canvas.width / 2 + width / 2),
            canvas.height
        )
        canvas.drawRect(rect, paint)
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