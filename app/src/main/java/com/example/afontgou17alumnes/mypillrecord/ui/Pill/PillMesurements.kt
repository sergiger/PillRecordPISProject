package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_pill_mesurements.*

class PillMesurements : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_mesurements)
        val image_view = findViewById(R.id.left_arrow) as ImageButton
        image_view.setOnClickListener {
            onBackPressed()
        }
        btn_arterialPressure.setOnClickListener {
            val intent = Intent(this,PillMesurements_Arterialpressure::class.java)
            startActivity(intent)
        }
        btn_glucoseAfter.setOnClickListener {
            val intent = Intent(this,PillMesurements_glucosaLevelAfter::class.java)
            startActivity(intent)

        }
    }
}
