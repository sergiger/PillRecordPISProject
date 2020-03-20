package com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toolbar
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.ui.login.LoginActivity
import kotlinx.android.synthetic.main.ajustes_activity.*
import kotlinx.android.synthetic.main.legal_main_activity.*
class privacy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.privacy_activity)

        back_legal_icon.setOnClickListener {
            onBackPressed()
        }
    }
}
