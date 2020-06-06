package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.*
import com.example.afontgou17alumnes.mypillrecord.data.model.statistics.StatisticEntry
import kotlinx.android.synthetic.main.activity_add_unplanned_activity.*
import kotlinx.android.synthetic.main.activity_today_modify_reminder.*
import kotlinx.android.synthetic.main.activity_today_modify_reminder.back_arrow
import kotlinx.android.synthetic.main.activity_today_modify_reminder.info_button
import kotlinx.android.synthetic.main.height_dialoge.view.*
import kotlinx.android.synthetic.main.number_dialog.view.*
import kotlinx.android.synthetic.main.number_dialog.view.OK
import kotlinx.android.synthetic.main.number_dialog.view.cancel
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import kotlinx.android.synthetic.main.time_dialog.view.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class TodayModifyReminder: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_modify_reminder)

        //Reminder
        var reminder = intent.extras.get("Reminder") as Reminder
        if(reminder is MedicineReminder){
            info_label.text = "Dose"
            info_button.text = reminder.dose.toString() + " " + reminder.doseUnit
        } else if(reminder is MeasurementReminder){
            info_label.text = "Value"
            info_button.text = "Enter a value"
        }else if(reminder is ActivityReminder){
            info_label.text = "Duration"
            info_button.text = reminder.duration.toString() + " min"
        }


        // View elements
        name_today_modify_reminder.text = reminder.getReminderName()
        if(reminder.getHour().minute < 10) {
            val h = reminder.getHour().hour
            val m = reminder.getHour().minute
            hour_button_today_modify_reminder.text = "$h:0$m"
        }else{
            hour_button_today_modify_reminder.text = reminder.getHour().toString()
        }

        //Set Listeners
        info_button.setOnClickListener {
            if(reminder is MeasurementReminder){
                //Height
                //Inflate the dialog with custom view
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.value_dialoge, null)
                //AlertDialogBuilder
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Value")
                //show dialog
                val  mAlertDialog = mBuilder.show()
                //login button click of custom layout
                mDialogView.OK_height.setOnClickListener {
                    val new_height = mDialogView.input_height.text.toString() //aquesta variable servirà per actualitzar l'edat
                    if(!new_height.equals("")) {
                        if(reminder is MeasurementReminder){
                            Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
                            reminder.value=new_height.toFloat()
                            info_button.text = reminder.value.toString() + " " + reminder.unit
                        }
                        //dismiss dialog
                        mAlertDialog.dismiss()
                    }


                }
                //cancel button click of custom layout
                mDialogView.cancel.setOnClickListener {
                    Toast.makeText(this,"cancel",Toast.LENGTH_SHORT).show()
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }

            }
            if(reminder is ActivityReminder){
                var new_duration=reminder.duration
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
                        reminder.duration=new_duration
                        info_button.text = reminder.duration.toString() + " min"
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
            else{
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
        today_modify_reminder_confirm.setOnClickListener {
            reminder.status = ReminderStatus.DONE
            if(reminder is MeasurementReminder)
                addStatistic(reminder)
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

    private fun addStatistic(reminder: MeasurementReminder){
        when(reminder.name){
            "Weight"-> {
                Controller.user.statistics.weightData.add(
                    StatisticEntry(reminder.value,reminder.date)
                )
            }
            "Heart rate"-> {
                Controller.user.statistics.heartRateData.add(
                    StatisticEntry(reminder.value, reminder.date)
                )
            }
            "Arterial pressure"-> {Controller.user.statistics.arterialPressureData.add(
                StatisticEntry(reminder.value,reminder.date)
            )
            }
            "Glucose (before eating)"-> {Controller.user.statistics.glucoseBeforeData.add(
                StatisticEntry(reminder.value,reminder.date)
            )
            }
            "Glucose (after eating)"-> {Controller.user.statistics.glucoseAfterData.add(
                StatisticEntry(reminder.value,reminder.date)
            )
            }
            "Temperature"-> {Controller.user.statistics.temperatureData.add(
                StatisticEntry(reminder.value,reminder.date)
            )
            }
        }
        Controller.StatisticToFirebase()
    }
}
