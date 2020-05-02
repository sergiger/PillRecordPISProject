package com.example.afontgou17alumnes.mypillrecord.ui.calendar.List

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.Reminder

class HistoricListAdapter (
    val activity: Fragment,
    val reminderList : ArrayList<Reminder>

) : BaseAdapter() {
    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity.context, R.layout.historic_row_item, null)
        val dateTextView : TextView = view.findViewById(R.id.txt_PillDate)
        val nameTextView : TextView = view.findViewById(R.id.txt_PillName)
        val infoTextView : TextView = view.findViewById(R.id.txt_PillInfo)
        val hourTextView : TextView = view.findViewById(R.id.txt_PillHour)
        val imageTextView : ImageView = view.findViewById(R.id.image_reminder_list)

        val i = reminderList[p0]

        // Comun a tots
        nameTextView.text = i.getReminderName()
        dateTextView.text = i.date.toString()
        hourTextView.text = i.time.toString()
        //De moment només està fet amb medicine a falta de configurar el model darrera
        if (i is MedicineReminder){
            infoTextView.text = i.dose.toString() + " " + i.doseUnit
            imageTextView.setImageResource(R.drawable.pill)
        }else if (i is MeasurementReminder){
            infoTextView.visibility = View.INVISIBLE
            imageTextView.setImageResource(R.drawable.pulse)
        }else if (i is ActivityReminder){
            infoTextView.text = i.duration.toString()
            imageTextView.setImageResource(R.drawable.olimpic)
        }
        return view
    }

    override fun getItem(p0: Int): Any {
        return reminderList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return reminderList.size
    }
}