package com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.legal_main_activity.*

class smartPatients : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.smartpatients_activity)

        back_legal_icon.setOnClickListener {
            onBackPressed()
        }
    }
}
