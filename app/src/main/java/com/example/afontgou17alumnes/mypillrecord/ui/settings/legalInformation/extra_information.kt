package com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.R

class extra_information : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.extra_information_activity)

        val image_view = findViewById<ImageView>(R.id.back_icon)
        image_view.setOnClickListener {
            onBackPressed()
        }
    }
}
