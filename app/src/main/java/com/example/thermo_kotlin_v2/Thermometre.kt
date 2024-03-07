package com.example.thermo_kotlin_v2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class Thermometre(context: Context, var tempEnDegC: Float = 20.0f, var unit: String = "C") : View(context) {

    private fun celsiusToFahrenheit(celsius: Float): Float = (celsius * 9 / 5) + 32

    private fun celsiusToKelvin(celsius: Float): Float = celsius + 273.15f

    fun setTemp(temp: Float) {
        this.tempEnDegC = temp
        invalidate()
    }

    fun updateUnit(unit: String) {
        this.unit = unit
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val tempMin = 0f
        val tempMax = 100f
        val tempProportion = (tempEnDegC - tempMin) / (tempMax - tempMin)

        val red = (255 * tempProportion).toInt()
        val blue = (255 * (1 - tempProportion)).toInt()
        val green = 0

        val paint = Paint().apply {
            color = Color.rgb(red, green, blue)
        }

        val drawableHeight = height - 120f
        val tempPosition = (tempEnDegC - tempMin) / (tempMax - tempMin) * drawableHeight

        canvas.drawLine((width / 2.0f), height - 60.0f - tempPosition, (width / 2.0f), height - 60.0f, paint)
        canvas.drawCircle((width / 2.0f), height - 60.0f, 60.0f, paint)

        when (unit) {
            "C" -> drawEchelleCelcius(canvas, paint)
            "F" -> drawEchelleFahrenheit(canvas, paint)
            "K" -> drawEchelleKelvin(canvas, paint)
        }
    }

    private fun drawEchelleCelcius(canvas: Canvas, paint: Paint) {
        val tempMin = 0f
        val tempMax = 100f
        val drawableHeight = height - 120f

        for (temp in 0..100 step 10) {
            val proportion = temp.toFloat() / (tempMax - tempMin)
            val yPos = height - 60f - (proportion * drawableHeight)

            canvas.drawLine((width / 2.0f) - 20, yPos, (width / 2.0f) + 20, yPos, paint)
            canvas.drawText(temp.toString(), (width / 2.0f) + 25, yPos + 10, paint)
        }
    }

    private fun drawEchelleFahrenheit(canvas: Canvas, paint: Paint) {
        val tempMinF = celsiusToFahrenheit(0f)
        val tempMaxF = celsiusToFahrenheit(100f)
        val drawableHeight = height - 120f

        for (temp in 32..212 step 18) {
            val proportion = (temp - 32).toFloat() / (212 - 32)
            val yPos = height - 60f - (proportion * drawableHeight)

            canvas.drawLine((width / 2.0f) - 20, yPos, (width / 2.0f) + 20, yPos, paint)
            canvas.drawText(temp.toString(), (width / 2.0f) + 25, yPos + 10, paint)
        }
    }

    private fun drawEchelleKelvin(canvas: Canvas, paint: Paint) {
        val tempMinK = celsiusToKelvin(0f)
        val tempMaxK = celsiusToKelvin(100f)
        val drawableHeight = height - 120f

        for (temp in 273..373 step 10) {
            val proportion = (temp - 273).toFloat() / (373 - 273)
            val yPos = height - 60f - (proportion * drawableHeight)

            canvas.drawLine((width / 2.0f) - 20, yPos, (width / 2.0f) + 20, yPos, paint)
            canvas.drawText(temp.toString(), (width / 2.0f) + 25, yPos + 10, paint)
        }
    }
}
