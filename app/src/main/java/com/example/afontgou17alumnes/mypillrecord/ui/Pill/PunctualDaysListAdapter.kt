package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.afontgou17alumnes.mypillrecord.R

class PunctualDaysListAdapter(
    val activity: TherapyInformation,
    var daysList : Array<String>
) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity, R.layout.pill_frequency_list, null)
        val infoTextView : TextView = view.findViewById(R.id.tw_hour)
        infoTextView.text = daysList[p0]
        val button : Button = view.findViewById(R.id.btn_delete_button)
        if(daysList.size== 1){
            button.visibility = View.GONE
        }
        else{
            button.setOnClickListener {
                (activity.therapy.frequency.listofpuntualdays as MutableList<*>).removeAt(p0)
                activity.createPunctualDaysList()
            }
        }

        return view
    }

    override fun getItem(p0: Int): Any {
        return daysList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return daysList.size
    }
}