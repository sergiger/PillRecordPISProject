package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.afontgou17alumnes.mypillrecord.R

class agenda_team : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agenda_team_activity)

        val image_view = findViewById(R.id.left_arrow) as ImageButton
        image_view.setOnClickListener {
            onBackPressed()
        }

    }
}
