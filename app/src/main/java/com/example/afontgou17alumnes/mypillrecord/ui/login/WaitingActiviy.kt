package com.example.afontgou17alumnes.mypillrecord.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller

class WaitingActiviy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.waiting_activiy)
        Controller.setContext_wait(this)
        Controller.ja_iniciat
    }
}
