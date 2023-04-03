package com.example.hww4


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import java.util.*


class AnalogClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private var height = 0
    private var width = 0
    private var radius = 0
    private var min = 0
    private var paint = Paint()


    private var second = (1..60).random()
    private var minute = (1..60).random()
    private var hour = (1..12).random().toDouble()


    private fun initClock() {
        height = getHeight()
        width = getWidth()
        min = height.coerceAtMost(width)
        radius = min / 2


    }


    @SuppressLint("DrawAllocation", "ResourceAsColor")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        initClock()
        check()
        drawCircle(canvas)
        drawHands(canvas)
        drawNumeral(canvas)
        postInvalidateDelayed(500)
        invalidate()

    }

    private fun check() {
        second++
        if (hour > 12) {
            hour = 1.0
        }
        if (second > 60) {
            minute += 1
            second = 1
        }
        if (minute > 60) {
            hour += 1
            minute = 1
        }
    }

    private fun drawHands(canvas: Canvas?) {
        Thread.sleep(1000)
        hourHand(canvas, hour, minute)
        minuteHand(canvas, minute, second)
        secondHand(canvas, second)
    }

    private fun drawNumeral(canvas: Canvas?) {
        paint.color = ContextCompat.getColor(context, R.color.black)
        paint.style = Paint.Style.FILL
        for (i in 1..12) {
            canvas?.save()
            val angle = 30 * i
            canvas?.rotate(angle.toFloat(), pivotX, pivotY)
            canvas?.drawRect(
                width / 2.toFloat() - 24f,
                (height / 2).toFloat() - 450f,
                radius.toFloat() + 24f,
                (height / 2).toFloat() - 500f,
                paint
            )
            canvas?.restore()
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun hourHand(canvas: Canvas?, hour: Double, minute: Int) {

        paint.color = ContextCompat.getColor(context, R.color.blue)
        paint.style = Paint.Style.FILL
        canvas?.save()
        val angle = (hour + minute / 60) * 30
        canvas?.rotate(angle.toFloat(), pivotX, pivotY)
        canvas?.drawRect(
            width / 2.toFloat() - 8f, height / 5.toFloat(), radius.toFloat() + 8f, radius + 60f,
            paint
        )
        canvas?.restore()
    }

    @SuppressLint("ResourceAsColor")
    private fun minuteHand(canvas: Canvas?, minute: Int, sec: Int) {

        paint.color = ContextCompat.getColor(context, R.color.red)
        paint.style = Paint.Style.FILL

        canvas?.save()
        val angle = (minute + sec / 60) * 6
        canvas?.rotate(angle.toFloat(), pivotX, pivotY)
        canvas?.drawRect(
            width / 2.toFloat() - 14f, height / 7.toFloat(), radius.toFloat() + 12f, radius + 110f,
            paint
        )
        canvas?.restore()

    }

    @SuppressLint("ResourceAsColor")
    private fun secondHand(canvas: Canvas?, sec: Int) {

        paint.color = ContextCompat.getColor(context, R.color.black)
        paint.style = Paint.Style.FILL
        canvas?.save()
        val angle = 6 * sec
        canvas?.rotate(angle.toFloat(), pivotX, pivotY)
        canvas?.drawRect(
            width / 2.toFloat() - 20f, height / 9.toFloat(), radius.toFloat() + 16f, radius + 160f,
            paint
        )
        canvas?.restore()
    }

    private fun drawCircle(canvas: Canvas?) {
        paint.color = Color.BLACK
        paint.strokeWidth = 20F
        paint.style = Paint.Style.STROKE
        canvas?.drawCircle(
            (width / 2).toFloat(), (height / 2).toFloat(), radius.toFloat() - 20f, paint
        )
    }
}






