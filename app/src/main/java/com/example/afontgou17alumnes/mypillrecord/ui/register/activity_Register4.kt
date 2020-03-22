package com.example.afontgou17alumnes.mypillrecord.ui.register

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity__register4.*
import kotlinx.android.synthetic.main.change_pasword_dialogue.*
import kotlinx.android.synthetic.main.gender_dialoge.*
import kotlinx.android.synthetic.main.share_team_activity.*

class activity_Register4 : AppCompatActivity() {
    var email=""
    var username=""
    var pasword=""
    var pasword_repeat=""
    var gender=""
    var age=10000
    var weight=10000
    var height=10000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__register4)
        //Això és per a veure el flow, no és per a fer que funcionin els botons de veritat


        btn_confirm_register.setOnClickListener {
            /*
            NO HE CONSEGUIT QUE EM FUNCIONI, SI POSO AIXÒ EL BOTÓ EM PETA I NO SÉ PERQUE
            I NO VEIG ON POT ESTAR L'ERROR

            email=input_email.text.toString()
            pasword=input_new_pasword.text.toString()
            pasword_repeat=input_repeat_new_pasword.text.toString()
            username=text_input_username.text.toString()
            gender=input_gender.text.toString()
            age=text_input_age.text.toString().toInt()
            weight=text_input_weight.text.toString().toInt()
            height=text_input_height.text.toString().toInt()*/
            //Aqui ara utilitzariem aquestes variables per fer totes les restriccions que volguéssim avans
            // Handler code here.
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
    }
}
