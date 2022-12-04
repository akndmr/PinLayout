package com.akndmr.pinlayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

class PinView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var isActive = false
    var radius = context.resources.getDimensionPixelSize(R.dimen.pin_view_radius).toFloat()
        set(value) {
            field = value
            animatedRadius = value
            diameter = value.times(2).toInt()
            this@PinView.invalidate()
        }
    private var animatedRadius = radius
    private var diameter: Int = radius.times(2).toInt()

    private val activePaint: Paint
        get() = Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            color = colorActive
        }

    private val defaultPaint: Paint
        get() = Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            color = colorDefault
        }

    @ColorInt
    var colorActive: Int = ContextCompat.getColor(context, R.color.white)
        set(value) {
            field = value
            this@PinView.invalidate()
        }

    @ColorInt
    var colorDefault = ContextCompat.getColor(context, R.color.blublu)
        set(value) {
            field = value
            this@PinView.invalidate()
        }

    private val animator: ValueAnimator
        get() = ValueAnimator.ofFloat(0f, radius).apply {
            duration = DEFAULT_ANIMATION_DURATION
            interpolator = DecelerateInterpolator()
            addUpdateListener { animator ->
                animatedRadius = animator.animatedValue as Float
                this@PinView.invalidate()
            }
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = if (isActive) activePaint else defaultPaint
        canvas?.drawCircle(radius, radius, animatedRadius, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val availableHeight = diameter - (paddingTop + paddingBottom)
        val availableWidth = diameter - (paddingLeft + paddingRight)
        diameter = Integer.min(availableWidth, availableHeight)
        setMeasuredDimension(diameter, diameter)
    }

    fun toggle() {
        isActive = isActive.not()
        animator.start()
    }

    fun isActive() = isActive

    companion object {
        const val DEFAULT_ANIMATION_DURATION = 250L
    }
}