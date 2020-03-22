package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_add_unplanned_measurement.*

class AddUnplannedMeasurement : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_unplanned_measurement)
        back_arrow.setOnClickListener{
            onBackPressed()
        }
        tick_unplanned_measurement.setOnClickListener{
            val tickIntent = Intent(this, MainActivity::class.java)
            startActivity(tickIntent)
        }
    }
}
