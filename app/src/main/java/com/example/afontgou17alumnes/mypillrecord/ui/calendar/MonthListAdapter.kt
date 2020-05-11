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
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.*
import java.time.LocalDate
import java.time.LocalTime

class MonthListAdapter(
    val activity: Fragment,
    var reminderList: ArrayList<Reminder>
)  : BaseAdapter() {
    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity.context, R.layout.month_list, null)
        val nameTextView : TextView = view.findViewById(R.id.reminder_name)
        val infoTextView : TextView = view.findViewById(R.id.reminder_info)
        val timeTextView : TextView = view.findViewById(R.id.reminder_time)
        val image : ImageView = view.findViewById(R.id.reminder_list_image)
        val statusTextView: TextView = view.findViewById(R.id.status_label_month)

        var i = reminderList[p0]

        if (i is MedicineReminder){
            nameTextView.text = i.name
            infoTextView.text = i.dose.toString() + " " + i.doseUnit
            timeTextView.text = i.time.toString()
            image.setImageResource(R.drawable.pill)
        }else if (i is MeasurementReminder){
            nameTextView.text = i.name
            if(i.value.equals(0F)) {
                infoTextView.visibility = View.INVISIBLE
            }else{
                infoTextView.text = i.value.toString() + " " + i.unit
            }
            timeTextView.text = i.time.toString()
            image.setImageResource(R.drawable.pulse)
        }else if (i is ActivityReminder){
            nameTextView.text = i.name
            infoTextView.text = i.duration.toString() + " min"
            timeTextView.text = i.time.toString()
            image.setImageResource(R.drawable.olimpic)
        }

        if(i.status == ReminderStatus.DONE){
            statusTextView.text = "DONE"
            statusTextView.setTextColor(Color.rgb(0,128,0))
        }else if(i.status == ReminderStatus.OMITTED){
            statusTextView.text = "OMITTED"
            statusTextView.setTextColor(Color.GRAY)
        }else{
            statusTextView.text = "TO DO"
            if(i.date < LocalDate.now() || (i.date == LocalDate.now() && i.time < LocalTime.now()))
                statusTextView.setTextColor(Color.RED)
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