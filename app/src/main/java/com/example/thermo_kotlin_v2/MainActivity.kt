package com.example.thermo_kotlin_v2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var manager: SensorManager
    private var tempSensor: Sensor? = null
    private lateinit var t: Thermometre

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val btnCelsius = Button(this).apply {
            text = "Celsius"
            setOnClickListener { t.updateUnit("C") }
        }

        val btnFahrenheit = Button(this).apply {
            text = "Fahrenheit"
            setOnClickListener { t.updateUnit("F") }
        }

        val btnKelvin = Button(this).apply {
            text = "Kelvin"
            setOnClickListener { t.updateUnit("K") }
        }

        layout.apply {
            addView(btnCelsius)
            addView(btnFahrenheit)
            addView(btnKelvin)
        }

        t = Thermometre(this)
        layout.addView(t)

        setContentView(layout)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        tempSensor = manager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        manager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            t.setTemp(event.values[0])
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
