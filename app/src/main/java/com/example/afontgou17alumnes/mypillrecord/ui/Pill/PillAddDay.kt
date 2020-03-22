package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_add_measurement.*
import kotlinx.android.synthetic.main.activity_pill_add_day.*
import kotlin.math.roundToInt

class PillAddDay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_add_day)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels * 0.8
        val height = dm.heightPixels * 0.75

        window.setLayout(width.roundToInt(), height.roundToInt())
        btn_cancel.setOnClickListener {
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
        btn_accept.setOnClickListener {
            Toast.makeText(this, "Day choose", Toast.LENGTH_SHORT).show()
            //day picker method
            onBackPressed()
        }
    }
}
