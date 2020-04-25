package com.example.afontgou17alumnes.mypillrecord.ui.calendar

import android.annotation.SuppressLint
import android.graphics.Color
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
import java.time.LocalTime

class ReminderListAdapter(
    val activity: Fragment,
    var reminderList: ArrayList<Reminder>
) : BaseAdapter() {
    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity.context, R.layout.reminder_list, null)
        val nameTextView : TextView = view.findViewById(R.id.reminder_name)
        val infoTextView : TextView = view.findViewById(R.id.reminder_info)
        val timeTextView : TextView = view.findViewById(R.id.reminder_time)
        val image : ImageView = view.findViewById(R.id.reminder_list_image)

        var i = reminderList[p0]

        //De moment només està fet amb medicine a falta de configurar el model darrera
        if (i is MedicineReminder){
            nameTextView.text = i.name
            infoTextView.text = i.dose.toString() + " " + i.doseUnit
            timeTextView.text = i.time.toString()
            image.setImageResource(R.drawable.pill)
        }else if (i is MeasurementReminder){
            nameTextView.text = i.name
            infoTextView.visibility = View.INVISIBLE
            timeTextView.text = i.time.toString()
            image.setImageResource(R.drawable.pulse)
        }else if (i is ActivityReminder){
            nameTextView.text = i.name
            infoTextView.text = i.duration.toString() + " min"
            timeTextView.text = i.time.toString()
            image.setImageResource(R.drawable.olimpic)
        }

        if(i.time < LocalTime.now()){
            infoTextView.setTextColor(Color.RED)
            nameTextView.setTextColor(Color.RED)
            timeTextView.setTextColor(Color.RED)
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