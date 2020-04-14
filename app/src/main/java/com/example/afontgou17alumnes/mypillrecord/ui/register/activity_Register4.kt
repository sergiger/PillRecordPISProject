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
            * Aqui només has de fer que s'omplin totes les variables.
            * També has d'enviar errors quan l'email ja estigui a la base de dades, és a dir, només una conta per email
            *
            * */
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
