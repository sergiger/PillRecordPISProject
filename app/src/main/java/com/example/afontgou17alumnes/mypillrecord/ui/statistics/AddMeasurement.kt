package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.afontgou17alumnes.mypillrecord.R
import kotlin.math.roundToInt

class AddMeasurement : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_measurement)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels*0.8
        val height = dm.heightPixels*0.6

        window.setLayout(width.roundToInt(), height.roundToInt())
    }
}
