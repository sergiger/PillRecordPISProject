package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.afontgou17alumnes.mypillrecord.R
import kotlin.math.roundToInt

class PillAddTime : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_add_time)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels*0.8
        val height = dm.heightPixels*0.6

        window.setLayout(width.roundToInt(), height.roundToInt())
    }
}
