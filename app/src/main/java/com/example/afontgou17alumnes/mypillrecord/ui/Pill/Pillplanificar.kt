package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pillplanificar.*

class Pillplanificar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pillplanificar)
        val image_view = findViewById<ImageButton>(R.id.left_arrow)
        image_view.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("goTo","Pill")
            startActivity(intent)
        }
        btn_medicacio.setOnClickListener{
            val intent = Intent(this,PillMedication::class.java)
            startActivity(intent)
        }
        btn_mesurements.setOnClickListener {
            val intent = Intent(this,PillMesurements::class.java)
            startActivity(intent)
        }
        btn_sports.setOnClickListener {
            val intent = Intent(this,PillSports::class.java)
            startActivity(intent)
        }

    }
}
