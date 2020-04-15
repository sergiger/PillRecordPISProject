package com.example.afontgou17alumnes.mypillrecord.ui.calendar

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.Reminder

class WeekListAdapter (
    val activity: Fragment,
    val reminderList : Array<Reminder>
) : BaseAdapter() {
    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity.context, R.layout.week_pill_child, null)
        val nameTextView : TextView = view.findViewById(R.id.txt_weekPillName)
        val infoTextView : TextView = view.findViewById(R.id.txt_weekPillInfo)
        val imageTextView : ImageView = view.findViewById(R.id.reminder_list_image)

        val i = reminderList[p0]

        //De moment només està fet amb medicine a falta de configurar el model darrera
        if (i is MedicineReminder){
            nameTextView.text = i.name
            infoTextView.text = i.dose.toString() + " " + i.doseUnit
            imageTextView.setImageResource(R.drawable.pill)
        }else if (i is MeasurementReminder){
            nameTextView.text = i.name
            infoTextView.visibility = View.INVISIBLE
            imageTextView.setImageResource(R.drawable.pulse)
        }else if (i is ActivityReminder){
            nameTextView.text = i.name
            infoTextView.text = i.duration.toString()
            imageTextView.setImageResource(R.drawable.olimpic)
        }
        return view
    }

    override fun getItem(p0: Int): Any {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return reminderList.size
    }
}