package com.example.afontgou17alumnes.mypillrecord.ui.today

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.Reminder
import kotlinx.android.synthetic.main.activity_today_modify_reminder.*
import java.time.LocalTime

class TodayModifyReminder: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_modify_reminder)

        //Reminder
        val reminder = intent.extras.get("Reminder") as Reminder
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
        back_arrow.setOnClickListener {
            onBackPressed()
        }
    }
}
