package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import kotlinx.android.synthetic.main.activity_pill_mesurements.*

class PillMesurements : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_mesurements)
        val image_view = findViewById<ImageButton>(R.id.left_arrow)
        image_view.setOnClickListener {
            val intent = Intent(this, Pillplanificar::class.java)
            startActivity(intent)
        }
        btn_arterialPressure.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("TitolMesurement","Arterial Pressure")
            intent.putExtra("UnitatsMesurement","mmHg")
            startActivity(intent)
        }
        btn_glucoseAfter.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("TitolMesurement","Glucose (after eating)")
            intent.putExtra("UnitatsMesurement","mg/dl")
            startActivity(intent)

        }
        btn_glucoseBefore.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("TitolMesurement","Glucose (before eating)")
            intent.putExtra("UnitatsMesurement","mg/dl")
            startActivity(intent)
        }
        btn_heartRate.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("TitolMesurement","Heart Rate")
            intent.putExtra("UnitatsMesurement","bpm")
            startActivity(intent)
        }
        btn_temperature.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("TitolMesurement","Temperature")
            intent.putExtra("UnitatsMesurement","Cº")
            startActivity(intent)
        }
        btn_weight.setOnClickListener {
            val intent = Intent(this,Pill_mesurements_info::class.java)
            intent.putExtra("TitolMesurement","Weight")
            intent.putExtra("UnitatsMesurement","kg")
            startActivity(intent)
        }
    }
}
