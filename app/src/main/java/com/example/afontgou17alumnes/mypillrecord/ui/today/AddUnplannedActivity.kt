package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_add_unplanned_activity.*
import kotlinx.android.synthetic.main.activity_add_unplanned_activity.back_arrow
import kotlinx.android.synthetic.main.activity_add_unplanned_activity.info_button
import kotlinx.android.synthetic.main.activity_add_unplanned_activity.view.*
import kotlinx.android.synthetic.main.height_dialoge.view.*
import kotlinx.android.synthetic.main.activity_add_unplanned_measurement.*
import kotlinx.android.synthetic.main.number_dialog.view.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.OK
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.cancel
import kotlinx.android.synthetic.main.time_dialog.view.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class AddUnplannedActivity : AppCompatActivity() {

    var day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var month=Calendar.getInstance().get(Calendar.MONTH)+1
    var year=Calendar.getInstance().get(Calendar.YEAR)
    var new_activity="Running"
    var hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    var minute=Calendar.getInstance().get(Calendar.MINUTE)
    var duration=15


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_unplanned_activity)

        if(minute < 10) {
            hour_button_unplanned_activity.text = "$hour:0$minute"
        }else{
            hour_button_unplanned_activity.text = "$hour:$minute"
        }

        back_arrow.setOnClickListener{
            go_back()
        }
        tick_unplanned_activity.setOnClickListener{
            save()
            go_home()
        }
        info_button.setOnClickListener{
            select_date(it)
        }
        hour_button_unplanned_activity.setOnClickListener{
            select_time()
        }
        duration_button.setOnClickListener{
            select_duration()
        }
        activity_name_button.setOnClickListener{
            showPopupMenu(it)
            /*val mDialogView = LayoutInflater.from(this).inflate(R.layout.text_dialog, null)

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Set activity")
            val mAlertDialog = mBuilder.show()
            mDialogView.OK.setOnClickListener {
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }*/
        }
    }

    fun select_duration(){
        var new_duration=this.duration
        //Duration
        //Inflate the dialog with custom view
        val mDialogView = android.view.LayoutInflater.from(this).inflate(com.example.afontgou17alumnes.mypillrecord.R.layout.duration_dialoge, null)
        //AlertDialogBuilder
        val mBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Duration")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.OK_height.setOnClickListener {
            val new_height = mDialogView.input_height.text.toString() //aquesta variable servirà per actualitzar l'edat
            if(!new_height.equals("")) {
                android.widget.Toast.makeText(this,"Added", android.widget.Toast.LENGTH_SHORT).show()
                new_duration=new_height.toInt()
                this.duration=new_duration
                duration_button.text=this.duration.toString()+" min"
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }
        //cancel button click of custom layout
        mDialogView.cancel.setOnClickListener {
            android.widget.Toast.makeText(this,"cancel", android.widget.Toast.LENGTH_SHORT).show()
            //dismiss dialog
            mAlertDialog.dismiss()
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
            //Aqui és on s'ha de posar on vols que s'escrigui el temps
            hour_button_unplanned_activity.text = hou+":"+min
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
    }

    fun select_date(it: View){
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
            set_ok_date(new_day,new_month,new_year,it)
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
    }

    fun set_ok_date(ini_day:Int,ini_month:Int,ini_year:Int,it: View){
        this.day=ini_day
        this.month=ini_month
        this.year=ini_year
        info_button.text = this.day.toString()+"/"+this.month.toString()+"/"+this.year.toString()
    }

    fun go_home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun go_back(){
        onBackPressed()
    }

    fun save(){
        var newReminder= Controller.createActivityReminder(
            this.new_activity, this.duration, LocalDate.of(this.year, this.month, this.day), LocalTime.of(this.hour, this.minute)
        )
        Controller.addReminder(newReminder)
        //Actualitza el shared preferences tmb
        /*val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("type_of_action","Save_and_go_home")
        startActivity(intent)*/
    }//cal completar

    private fun showPopupMenu(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.activity_popup_menu, menu)
        setOnMenuItemClickListener { item ->
            new_activity=item.title.toString()
            view.activity_name_button.text = new_activity.toString()
            true
        }
        show()
    }
}
