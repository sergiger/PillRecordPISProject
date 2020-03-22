package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.afontgou17alumnes.mypillrecord.R
import kotlin.math.roundToInt

class AddUnplannedEntry : AppCompatActivity() {

    companion object {
        fun newInstance() =
            AddUnplannedEntry()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_unplanned_entry)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels*0.8
        val height = dm.heightPixels*0.35

        window.setLayout(width.roundToInt(), height.roundToInt())
    }
}
