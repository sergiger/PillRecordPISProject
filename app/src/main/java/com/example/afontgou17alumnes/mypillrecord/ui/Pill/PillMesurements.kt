package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_pill_mesurements.*

class PillMesurements : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_mesurements)
        val image_view = findViewById<ImageButton>(R.id.left_arrow)
        image_view.setOnClickListener {
            onBackPressed()
        }
        btn_arterialPressure.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("type_of_measurement","      ARTERIAL PRESSURE")
            intent.putExtra("unitats","mmHg")
            startActivity(intent)
        }
        btn_glucoseAfter.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("type_of_measurement","      GLUCOSA AFTER")
            intent.putExtra("unitats","mg/dl")
            startActivity(intent)

        }
        btn_glucoseBefore.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("type_of_measurement","      GLUCOSA BEFORE")
            intent.putExtra("unitats","mg/dl")
            startActivity(intent)
        }
        btn_heartRate.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("type_of_measurement","      HEART FREQUENCY")
            intent.putExtra("unitats","bpm")
            startActivity(intent)
        }
        btn_temperature.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("type_of_measurement","      TEMPERATURE")
            intent.putExtra("unitats","Cº")
            startActivity(intent)
        }
        btn_weight.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("type_of_measurement","      WEIGHT")
            intent.putExtra("unitats","kg")
            startActivity(intent)
        }
    }
}
