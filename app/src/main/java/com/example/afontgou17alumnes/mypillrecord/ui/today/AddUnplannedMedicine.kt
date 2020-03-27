package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_add_unplanned_activity.back_arrow
import kotlinx.android.synthetic.main.activity_add_unplanned_measurement.*
import kotlinx.android.synthetic.main.activity_add_unplanned_medicine.*
import kotlinx.android.synthetic.main.activity_add_unplanned_medicine.date_button
import kotlinx.android.synthetic.main.activity_pill_add_time.*
import kotlinx.android.synthetic.main.activity_pill_add_time.view.*
import kotlinx.android.synthetic.main.number_dialog.*
import kotlinx.android.synthetic.main.number_dialog.view.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.OK
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.cancel
import kotlinx.android.synthetic.main.time_dialog.view.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class AddUnplannedMedicine : AppCompatActivity() {

    var day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var month=Calendar.getInstance().get(Calendar.MONTH)+1
    var year=Calendar.getInstance().get(Calendar.YEAR)
    var hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    var minute=Calendar.getInstance().get(Calendar.MINUTE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_unplanned_medicine)

        //Set Listeners
        back_arrow.setOnClickListener{
            go_back()
        }
        tick_unplanned_medicine.setOnClickListener{
            save()
            go_home()
        }
        date_button.setOnClickListener{
            select_date()
        }
        hour_button_unplanned_medicine.setOnClickListener{
            select_time()
        }
        dose_button.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.number_dialog, null)
            //Set Number Picker
            mDialogView.number_Picker.minValue = 1
            mDialogView.number_Picker.maxValue = 100
            mDialogView.number_Picker.wrapSelectorWheel = false

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Set dose")
            val mAlertDialog = mBuilder.show()
            mDialogView.OK.setOnClickListener {
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
        }
        medicine_name_button.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.search_dialog, null)

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Set medicine")
            val mAlertDialog = mBuilder.show()
            mDialogView.OK.setOnClickListener {
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
        }
    }

    fun select_time(){
        var new_Hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        var new_minute=Calendar.getInstance().get(Calendar.MINUTE)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.time_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Set date")
        val mAlertDialog = mBuilder.show()
        mDialogView.time_Picker.setOnTimeChangedListener { view, hour, minute ->
            new_Hour=hour
            new_minute=minute
        }
        mDialogView.OK.setOnClickListener {
            this.hour=new_Hour
            this.minute=new_minute
            var min=this.minute.toString()
            var hou=this.hour.toString()
            if(this.minute<10){
                min="0"+this.minute.toString()
            }
            if(this.hour<10){
                hou="0"+this.hour.toString()
            }
            hour_button_unplanned_medicine.text = hou+":"+min
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
    }

    fun select_date(){
        var new_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        var new_month=Calendar.getInstance().get(Calendar.MONTH)+1
        var new_year=Calendar.getInstance().get(Calendar.YEAR)

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.date_dialoge, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Set specific dates")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //Aqui guardo la data INI seleccionada
        //i també poso la data correcta al DatePicker de INICI amb la data d'avui
        val datePicker_ini = mDialogView.findViewById<DatePicker>(R.id.date_Picker)
        val today = Calendar.getInstance()
        datePicker_ini.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            new_day=day
            new_month=month
            new_year=year
            //val msg = "You Selected: $day/$month/$year"
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        //Aqui faig els listeners dels dos botons
        mDialogView.OK.setOnClickListener {
            Toast.makeText(this,"work in progress",Toast.LENGTH_LONG).show()
            set_ok_date(new_day,new_month,new_year)
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
    }

    fun set_ok_date(ini_day:Int,ini_month:Int,ini_year:Int){
        this.day=ini_day
        this.month=ini_month
        this.year=ini_year
        date_button.setText(this.day.toString()+"//"+this.month.toString()+"//"+this.year.toString())

    }

    fun go_home(){
        val intent = Intent(this, MainActivity::class.java);
        startActivity(intent);
    }

    fun go_back(){
        onBackPressed()
    }

    fun save(){}//cal completar


}
