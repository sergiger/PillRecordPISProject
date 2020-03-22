package com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.legal_main_activity.*
import kotlinx.android.synthetic.main.legal_main_activity.back_legal_icon
import kotlinx.android.synthetic.main.privacy_activity.*

class extra_information : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.extra_information_activity)

        val image_view = findViewById(R.id.back_icon) as ImageView
        image_view.setOnClickListener {
            onBackPressed()
        }
    }
}
