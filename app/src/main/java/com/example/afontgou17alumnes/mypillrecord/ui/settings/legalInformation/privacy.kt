package com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.ui.login.LoginActivity
import kotlinx.android.synthetic.main.ajustes_activity.*
import kotlinx.android.synthetic.main.legal_main_activity.*
import kotlinx.android.synthetic.main.legal_main_activity.back_legal_icon
import kotlinx.android.synthetic.main.privacy_activity.*

class privacy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.privacy_activity)

        back_legal_icon.setOnClickListener {
            onBackPressed()
        }
        print_icon.setOnClickListener {
            Toast.makeText(this,"downloading PDF",Toast.LENGTH_SHORT).show()
        }
    }
}
