package com.example.afontgou17alumnes.mypillrecord.ui.calendar

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.*

class ReminderListAdapter (
    val activity: Fragment,
    val reminderList : Array<Reminder>
) : BaseAdapter() {
    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity.context, R.layout.reminder_list, null)
        val nameTextView : TextView = view.findViewById(R.id.reminder_name)
        val infoTextView : TextView = view.findViewById(R.id.reminder_info)
        val timeTextView : TextView = view.findViewById(R.id.reminder_time)
        val imageTextView : ImageView = view.findViewById(R.id.reminder_list_image)

        val i = reminderList[p0]

        //De moment només està fet amb medicine a falta de configurar el model darrera
        if (i is MedicineReminder){
            nameTextView.text = i.name
            infoTextView.text = i.dose.toString() + " " + i.doseUnit
            timeTextView.text = i.time.toString()
            imageTextView.setImageResource(R.drawable.pill)
        }else if (i is MeasurementReminder){
            nameTextView.text = i.name
            infoTextView.visibility = View.INVISIBLE
            timeTextView.text = i.time.toString()
            imageTextView.setImageResource(R.drawable.pulse)
        }else if (i is ActivityReminder){
            nameTextView.text = i.name
            infoTextView.text = i.duration.toString()
            timeTextView.text = i.time.toString()
            imageTextView.setImageResource(R.drawable.olimpic)
        }


        return view;
    }

    override fun getItem(p0: Int): Any {
        return 1;
    }

    override fun getItemId(p0: Int): Long {
        return 0;
    }

    override fun getCount(): Int {
        return reminderList.size;
    }
}