package com.example.afontgou17alumnes.mypillrecord.ui.today

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.*
import kotlinx.android.synthetic.main.activity_today_modify_reminder.*

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
            info_label.text = "Value"
            info_button.text = reminder.duration.toString() + " min"
        }


        //View elements
        name_today_modify_reminder.text = reminder.getReminderName()
        hour_button_today_modify_reminder.text = reminder.getHour().toString()

        //Set Listeners
        today_modify_reminder_confirm.setOnClickListener {
            reminder.status = ReminderStatus.DONE
            onBackPressed()
        }
        today_modify_reminder_omit.setOnClickListener{
            reminder.status = ReminderStatus.OMITTED
            onBackPressed()
        }
        back_arrow.setOnClickListener {
            onBackPressed()
        }
    }
}
