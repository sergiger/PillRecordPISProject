package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.afontgou17alumnes.mypillrecord.R

class PillMesurements : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_mesurements)
        val image_view = findViewById(R.id.left_arrow) as ImageButton
        image_view.setOnClickListener {
            onBackPressed()
        }
    }
}
