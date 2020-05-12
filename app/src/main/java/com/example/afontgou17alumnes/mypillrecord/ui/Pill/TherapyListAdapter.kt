package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.ActivityTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MeasurementTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MedicineTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import java.time.LocalTime

class TherapyListAdapter(
    val activity: Fragment,
    var therapyList: ArrayList<Therapy>
) : BaseAdapter() {
    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity.context, R.layout.therapy_list, null)
        val nameTextView : TextView = view.findViewById(R.id.therapy_name)
        val infoTextView : TextView = view.findViewById(R.id.therapy_info)
        val freqTextView : TextView = view.findViewById(R.id.therapy_frequency)
        val fromTextView : TextView = view.findViewById(R.id.therapy_from)
        val toTextView : TextView = view.findViewById(R.id.therapy_to)
        val image : ImageView = view.findViewById(R.id.therapy_list_image)

        var i = therapyList[p0]


        if (i is MedicineTherapy){
            nameTextView.text = i.medicine
            infoTextView.text = i.dose.toString() + " " + i.units
            image.setImageResource(R.drawable.pill)
        }else if (i is MeasurementTherapy){
            nameTextView.text = i.measurementType
            infoTextView.visibility = View.INVISIBLE
            infoTextView.height = 0
            image.setImageResource(R.drawable.pulse)
        }else if (i is ActivityTherapy){
            nameTextView.text = i.activityType
            infoTextView.text = i.duration.toString() + " min"
            image.setImageResource(R.drawable.olimpic)
        }

        //Set frequency text
        when(i.frequency.type){
            1->freqTextView.text = "Each day, ${i.hours.size} time(s) a day"
            2->freqTextView.text = "Each ${i.frequency.eachtimedose} day(s), ${i.hours.size} time(s) a day"
            3->{
                var text = ""
                for( count in 0 until i.frequency.specificweekdays.size -1){
                    if(i.frequency.specificweekdays[count] != "")
                        text += "${i.frequency.specificweekdays[count]} "
                }
                text += ", ${i.hours.size} time(s) a day"
                freqTextView.text = text
            }
            4->freqTextView.text = "Punctual day(s), ${i.hours.size} time(s) a day"
        }

        //Set from/to text
        fromTextView.text = i.frequency.startDate
        toTextView.text = i.frequency.endDate

        return view
    }

    override fun getItem(p0: Int): Any {
        return therapyList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return therapyList.size
    }
}