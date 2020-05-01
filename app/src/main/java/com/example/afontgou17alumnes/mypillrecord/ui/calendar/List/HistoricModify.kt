package com.example.afontgou17alumnes.mypillrecord.ui.calendar.List

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.*
import kotlinx.android.synthetic.main.activity_calendar_modify.*
import kotlinx.android.synthetic.main.activity_today_modify_reminder.back_arrow
import kotlinx.android.synthetic.main.activity_today_modify_reminder.hour_button_today_modify_reminder
import kotlinx.android.synthetic.main.activity_today_modify_reminder.info_button
import kotlinx.android.synthetic.main.activity_today_modify_reminder.info_label
import kotlinx.android.synthetic.main.activity_today_modify_reminder.name_today_modify_reminder
import kotlinx.android.synthetic.main.activity_today_modify_reminder.today_modify_reminder_confirm
import kotlinx.android.synthetic.main.activity_today_modify_reminder.today_modify_reminder_omit
import kotlinx.android.synthetic.main.number_dialog.view.*
import kotlinx.android.synthetic.main.number_dialog.view.OK
import kotlinx.android.synthetic.main.number_dialog.view.cancel
import kotlinx.android.synthetic.main.time_dialog.view.*
import java.time.LocalTime
import java.util.*

class HistoricModify : AppCompatActivity() {
    val status_types = arrayOf("Confirmed","Skipped")
    var status = "Set"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_modify)

        //Reminder
        var reminder = intent.extras.get("Reminder") as Reminder
        if(reminder is MedicineReminder){
            info_label.text = "Dose"
            info_button.text = reminder.dose.toString() + " " + reminder.doseUnit
        } else if(reminder is MeasurementReminder){
            info_label.text = "Value"
            info_button.text = "Enter a value"
        }else if(reminder is ActivityReminder) {
            info_label.text = "Duration"
            info_button.text = reminder.duration.toString() + " min"
        }


        //View elements common for reminders
        name_today_modify_reminder.text = reminder.getReminderName()
        hour_button_today_modify_reminder.text = reminder.getHour().toString()
        // fer
        status_button.text = reminder.getReminderStatus().toString()
        date_button.text = reminder.getReminderDate().toString()

        //Set Listeners
        info_button.setOnClickListener {
            var infoValue = 0
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.number_dialog, null)
            //Set Number Picker
            mDialogView.number_Picker.minValue = 1
            mDialogView.number_Picker.maxValue = 100
            mDialogView.number_Picker.wrapSelectorWheel = false

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            //Set title of dialog
            if(reminder is MedicineReminder)  mBuilder.setTitle("Set dose")
            else if(reminder is MeasurementReminder) mBuilder.setTitle("Set value")
            else mBuilder.setTitle("Set duration")

            val mAlertDialog = mBuilder.show()

            mDialogView.number_Picker.setOnValueChangedListener { picker, oldVal, newVal ->
                infoValue = newVal
            }
            mDialogView.OK.setOnClickListener {
                if(reminder is MedicineReminder){
                    reminder.dose = infoValue
                    info_button.text = reminder.dose.toString() + " " + reminder.doseUnit
                }
                else if(reminder is MeasurementReminder){
                    reminder.value = infoValue.toFloat()
                    info_button.text = reminder.value.toString() + " " + reminder.unit
                }
                else if(reminder is ActivityReminder){
                    reminder.duration = infoValue
                    info_button.text = reminder.duration.toString() + " min"
                }

                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
        }
        hour_button_today_modify_reminder.setOnClickListener {
            var newHour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            var newMinute= Calendar.getInstance().get(Calendar.MINUTE)
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.time_dialog, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Set time")
            val mAlertDialog = mBuilder.show()
            mDialogView.time_Picker.setOnTimeChangedListener { view, hour, minute ->
                newHour = hour
                newMinute = minute
            }
            mDialogView.OK.setOnClickListener {
                reminder.time = LocalTime.of(newHour, newMinute)
                hour_button_today_modify_reminder.text = reminder.time.toString()

                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
        }
        // Fer
        date_button.setOnClickListener {
            select_date()
        }

        status_button.setOnClickListener {
            select_status(reminder)
        }

        today_modify_reminder_confirm.setOnClickListener {
            reminder.status = ReminderStatus.DONE
            val returnIntent = Intent()
            returnIntent.putExtra("Reminder", reminder)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
        today_modify_reminder_omit.setOnClickListener{
            reminder.status = ReminderStatus.OMITTED
            val returnIntent = Intent()
            returnIntent.putExtra("Reminder", reminder)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
        back_arrow.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra("Reminder", reminder)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
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
            date_button.text = new_day.toString() +"//"+ new_month.toString()+"//"+ new_year.toString()
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
    }

    fun select_status(reminder: Reminder) {
        var infoValue = 0
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.number_dialog, null)
        //Set Number Picker
        mDialogView.number_Picker.minValue = 0
        mDialogView.number_Picker.maxValue = 1
        mDialogView.number_Picker.displayedValues = status_types
        mDialogView.number_Picker.wrapSelectorWheel = false

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        //Set title of dialog
        mBuilder.setTitle("Set duration")

        val mAlertDialog = mBuilder.show()

        mDialogView.number_Picker.setOnValueChangedListener { picker, oldVal, newVal ->
            infoValue = newVal
        }
        mDialogView.OK.setOnClickListener {
            info_button.text = reminder.status.toString()
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
    }
}