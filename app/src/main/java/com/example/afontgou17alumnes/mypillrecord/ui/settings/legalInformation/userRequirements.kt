package com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.legal_main_activity.*
import kotlinx.android.synthetic.main.legal_main_activity.back_legal_icon
import kotlinx.android.synthetic.main.privacy_activity.*

class userRequirements : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_requirements_activity)

        back_legal_icon.setOnClickListener {
            onBackPressed()
        }

        print_icon.setOnClickListener {
            Toast.makeText(this,"downloading PDF", Toast.LENGTH_SHORT).show()
        }
    }
}
