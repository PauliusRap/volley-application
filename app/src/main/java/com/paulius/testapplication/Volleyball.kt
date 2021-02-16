package com.paulius.testapplication

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Canvas
import kotlin.math.sqrt

private const val GRAVITY = 10
private const val AIR_RESISTANCE = 3

class Volleyball(private val resources: Resources) : PhysicsObject() {

    override var x = 100f
    override var y = 100f
    override var velocityX = 0f
    override var velocityY = 0f
    val elasticity = 0.8f
    val radius = 100f
    private var previousRotation = 0f
    private var rotation = 0f
    private var resource: VolleyballVector = VolleyballVector.Rotate0

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun draw(canvas: Canvas) {
        rotation = velocityX + velocityY

        resource = chooseRotatedDrawable(resource, rotation, previousRotation)

        previousRotation = rotation

        resources.getDrawable(resource.resId).apply {
            setBounds(
                x.toInt(),
                y.toInt(),
                x.toInt() + radius.toInt(),
                y.toInt() + radius.toInt()
            )
            draw(canvas)
        }
    }
    
    override fun update(timeDelta: Int, maxX: Int, maxY: Int) {

        //Setting sideways velocity. No matter which way the ball is going, air stops it by a certain amount
        if (velocityX > AIR_RESISTANCE) {
                velocityX -= AIR_RESISTANCE
            } else {
                velocityX += AIR_RESISTANCE
            }
        //Setting vertical velocity.
        velocityY = (velocityY + GRAVITY - AIR_RESISTANCE)

        x = if (velocityX < AIR_RESISTANCE
            && velocityX > -AIR_RESISTANCE) {
            x
        } else {
            (x + (timeDelta / 100f * velocityX))
        }
        y = (y + (timeDelta / 100f * velocityY))
        
        if (x < 0) {
            velocityX = (-velocityX * elasticity)
            x = (0f)
        } else if (x > maxX - radius) {
            velocityX = (-velocityX * elasticity)
            x = (maxX - radius)
        }
        if (y < 0) {
            velocityY = (-velocityY * elasticity)
            y = (0f)
        } else if (y > maxY - radius) {
            velocityY = (-velocityY * elasticity)
            y = (maxY - radius)
        }
    }

    fun addXVelocity(velocity: Int) {
        velocityX += velocity
    }

    fun addYVelocity(velocity: Int) {
        velocityY += velocity
    }

    private fun chooseRotatedDrawable(
        previousResource: VolleyballVector,
        currentRotation: Float,
        previousRotation: Float
    ): VolleyballVector {
        return when {
            currentRotation == previousRotation -> previousResource
            currentRotation < previousRotation -> previousResource.next()
            else -> previousResource.previous()
        }
    }

    fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Int {
        return sqrt(((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)).toDouble()).toInt()
    }

    fun isTouching(x: Float, y: Float): Boolean {
        return distance(x, y, x, y) <= this.radius
    }
}
