package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.share_team_activity.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
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

        val image_view = findViewById<ImageButton>(R.id.left_arrow)
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
            Controller.share_to(new_email,this)
            /*if(new_email_exists(new_email)) {
                /*var id: Int = radio_group.checkedRadioButtonId
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
                }*/
            }else{
                Toast.makeText(this,"Email does not exist",Toast.LENGTH_SHORT).show()
            }*/
        }
    }


    fun radio_button_click(view: View){
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(radio_group.checkedRadioButtonId)
        Toast.makeText(applicationContext,"On click : ${radio.text}",
            Toast.LENGTH_SHORT).show()
    }


    fun go_home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun go_home_succesfully(){
        Toast.makeText(this,"Shared succesfully",Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun select_start_end_dates(){
        var new_ini_day=0
        var new_ini_month=0
        var new_ini_year=0
        var new_end_day=0
        var new_end_month=0
        var new_end_year=0


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
            new_ini_day=day
            new_ini_month=month
            new_ini_year=year
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
            new_end_day=day
            new_end_month=month
            new_end_year=year
            //val msg = "You Selected: $day/$month/$year"
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        //Aqui faig els listeners dels dos botons
        mDialogView.OK.setOnClickListener {
            Toast.makeText(this,"work in progress",Toast.LENGTH_LONG).show()
            set_ok_date(new_ini_day,new_ini_month,new_ini_year,new_end_day,new_end_month,new_end_year)
            go_home()
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
    }

    fun set_ok_date(ini_day:Int,ini_month:Int,ini_year:Int,end_day:Int,end_month:Int,end_year:Int){
        this.end_day=end_day
        this.end_month=end_month
        this.end_year=end_year

        this.ini_day=ini_day
        this.ini_month=ini_month
        this.ini_year=ini_year
    }



}
