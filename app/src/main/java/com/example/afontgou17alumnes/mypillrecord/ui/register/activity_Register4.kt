package com.example.afontgou17alumnes.mypillrecord.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity__register4.*
import kotlinx.android.synthetic.main.activity__register4.view.*

class activity_Register4 : AppCompatActivity() {
    var email="88"
    var username="88"
    var pasword="88888888888888"
    var pasword_repeat=""
    var year_Birth=1599
    var gender="Masculin"
    var weight=10000F
    var height=10000F


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__register4)
        //Això és per a veure el flow, no és per a fer que funcionin els botons de veritat
        text_input_gender.setOnClickListener {
            showPopupMenu(it)
            text_input_gender.setText(gender.toString())
        }

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
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            createAccount()
        }
    }

    fun showPopupMenu(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.gender_popup_menu, menu)
        setOnMenuItemClickListener { item ->
            //Toast.makeText(view.context, "You Clicked : ${item.title}", Toast.LENGTH_SHORT).show()
            gender=item.title.toString()
            view.text_input_gender.setText(gender.toString())
            true
        }
        show()
    }

    fun createAccount(){
        Controller.createAccount(this.email,this.username,this.pasword,this.gender,this.year_Birth,this.weight,this.height)
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("type_of_action","Save_share_Create_Account_Go_Home")
        startActivity(intent)
    }
}
