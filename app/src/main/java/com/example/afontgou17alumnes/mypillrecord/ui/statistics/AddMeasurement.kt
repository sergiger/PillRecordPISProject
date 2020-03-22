package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_add_measurement.*
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
        cancel.setOnClickListener {
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
        add.setOnClickListener {
            var new_input=enter_value.text.toString()
            if(!new_input.equals("")){
                //Aqui fariem algo amb aquest valor, per ara simplement tirarem enrere
                Toast.makeText(this,"Added successfully",Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }

    }
}
