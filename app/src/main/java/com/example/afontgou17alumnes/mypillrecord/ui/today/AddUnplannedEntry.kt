package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_add_unplanned_entry.*
import kotlin.math.roundToInt

class AddUnplannedEntry : AppCompatActivity() {

    companion object {
        fun newInstance() =
            AddUnplannedEntry()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_unplanned_entry)

        //Set intents
        medicine_entry_button.setOnClickListener{
            val medicineIntent = Intent(this, AddUnplannedMedicine::class.java)
            startActivity(medicineIntent)
        }
        measurement_entry_button.setOnClickListener{
            val measurementIntent = Intent(this, AddUnplannedMeasurement::class.java)
            startActivity(measurementIntent)
        }
        activity_entry_button.setOnClickListener{
            val activityIntent = Intent(this, AddUnplannedActivity::class.java)
            startActivity(activityIntent)
        }

        //Set dimensions
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels*0.8
        val height = dm.heightPixels*0.35
        window.setLayout(width.roundToInt(), height.roundToInt())
    }

}
