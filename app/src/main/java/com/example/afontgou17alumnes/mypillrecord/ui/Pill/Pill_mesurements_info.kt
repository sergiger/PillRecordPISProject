package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_pill_sports.btn_Save
import kotlinx.android.synthetic.main.activity_pill_sports.btn_frequency
import kotlinx.android.synthetic.main.activity_pill_sports.btn_from
import kotlinx.android.synthetic.main.pill_mesurements_activity.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import java.util.*

class Pill_mesurements_info : AppCompatActivity() {

    var ini_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var ini_month=Calendar.getInstance().get(Calendar.MONTH)+1
    var ini_year=Calendar.getInstance().get(Calendar.YEAR)
    var end_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var end_month=Calendar.getInstance().get(Calendar.YEAR)
    var end_year=Calendar.getInstance().get(Calendar.YEAR)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pill_mesurements_activity)

        val bundle:Bundle = intent.extras
        val title = bundle.get("type_of_measurement")
        val unitat =bundle.get("unitats")
        toolbar_title.text = title.toString()
        unitats_output.text=unitat.toString()

        val image_view = findViewById<ImageButton>(R.id.left_arrow)
        btn_from.text = ""
        image_view.setOnClickListener {
            go_back()
        }
        btn_from.setOnClickListener {
            select_start_end_dates()
        }
        btn_frequency.setOnClickListener {
            val intent = Intent(this, PillFrequency::class.java)
            startActivity(intent)
        }
        btn_Save.setOnClickListener {
            Toast.makeText(this, "New plan added", Toast.LENGTH_LONG).show()
            save_mesurement()
            go_home()
        }
    }

    fun select_start_end_dates(){
        var new_ini_day=this.ini_day
        var new_ini_month=this.ini_month
        var new_ini_year=this.ini_year
        var new_end_day=this.end_day
        var new_end_month=this.end_month
        var new_end_year=this.end_year


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
            mAlertDialog.dismiss()
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

        var data_ini=this.ini_day.toString()+"//"+this.ini_month.toString()+"//"+this.ini_year.toString()

        this.ini_day=ini_day
        this.ini_month=ini_month
        this.ini_year=ini_year

        var data_end=this.end_day.toString()+"//"+this.end_month.toString()+"//"+this.end_year.toString()

        btn_from.text = data_ini+" to "+data_end
    }


    fun go_home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun go_back(){
        onBackPressed()
    }

    fun save_mesurement(){}//cal completar
}



