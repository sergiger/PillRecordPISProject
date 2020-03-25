package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.share_team_activity.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import kotlinx.android.synthetic.main.weight_dialoge.view.*
import kotlinx.android.synthetic.main.weight_dialoge.view.cancel
import java.util.*

class share_team : AppCompatActivity() {

    var email=""
    var ini_day=0
    var ini_month=0
    var ini_year=0
    var end_day=0
    var end_month=0
    var end_year=0
    //Aquestes variables ja funcionen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_team_activity)

        val image_view = findViewById(R.id.left_arrow) as ImageButton
        image_view.setOnClickListener {
            onBackPressed()
        }

        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext," On checked change : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
            })

        btn_share.setOnClickListener{
            // Get the checked radio button id from radio group
            var new_email=input_email.text.toString()
            if(new_email_exists(new_email)) {
                var id: Int = radio_group.checkedRadioButtonId
                if (id!=-1){ // If any radio button checked from radio group
                    // Get the instance of radio button using id
                    val radio:RadioButton = findViewById(id)
                    Toast.makeText(applicationContext,"On button click : ${radio.text}",
                        Toast.LENGTH_SHORT).show()
                    when(radio.text){
                        "Day"->{//Day
                            go_home_succesfully()
                            }
                        "Week"-> {//Week
                            go_home_succesfully()
                        }
                        "Month"->{//Month
                            go_home_succesfully()
                        }
                        "Specific dates"->{//Special date
                            select_start_end_dates()
                        }
                    }
                }else{
                    // If no radio button checked in this radio group
                    Toast.makeText(applicationContext,"On button click : nothing selected",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun radio_button_click(view: View){
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(radio_group.checkedRadioButtonId)
        Toast.makeText(applicationContext,"On click : ${radio.text}",
            Toast.LENGTH_SHORT).show()
    }


    fun go_home(){
        val intent = Intent(this, MainActivity::class.java);
        startActivity(intent);
    }

    fun go_home_succesfully(){
        Toast.makeText(this,"Shared succesfully",Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java);
        startActivity(intent);
    }

    fun select_start_end_dates(){

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.specific_dates_dialoge, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Set specific dates")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //Aqui guardo la data INI seleccionada
        //i també poso la data correcta al DatePicker de INICI amb la data d'avui
        val datePicker_ini = mDialogView.findViewById<DatePicker>(R.id.date_Picker_ini)
        val today = Calendar.getInstance()
        datePicker_ini.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            ini_day=day
            ini_month=month
            ini_year=year
            //val msg = "You Selected: $day/$month/$year"
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        //Aqui guardo la data END seleccionada
        //i també poso la data correcta al DatePicker de END amb la data d'avui
        val datePicker_end = mDialogView.findViewById<DatePicker>(R.id.date_Picker_end)
        datePicker_end.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            end_day=day
            end_month=month
            end_year=year
            //val msg = "You Selected: $day/$month/$year"
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        //Aqui faig els listeners dels dos botons
        mDialogView.OK.setOnClickListener {
            Toast.makeText(this,"work in progress",Toast.LENGTH_LONG).show()
            //Ara ja estan les hores agafades correctament, ara hauriem de compartir
            //Les terapies actives i no actives entre aquestes dates
            go_home()
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
    }

    fun new_email_exists(new_email: String) :Boolean{
        var retorn= true
        //Aqui conectariem amb la base de dades i buscariem si aquest email existeix
        //Ara per ara simplement li poso true per a poder treballar i fer proves
        return retorn
    }

}
