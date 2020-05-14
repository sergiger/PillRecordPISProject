package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.R

class HoursListAdapter(
    val activity: TherapyInformation,
    var hourList : ArrayList<String>
) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity, R.layout.pill_frequency_list, null)
        val infoTextView : TextView = view.findViewById(R.id.tw_hour)
        infoTextView.text = hourList[p0]
        val button : Button = view.findViewById(R.id.btn_delete_button)
        if(hourList.size== 1){
            button.visibility = View.GONE
        }
        else{
            button.setOnClickListener {
                activity.therapy.hours.removeAt(p0)
                activity.createHoursList()
            }
        }

        return view
    }

    override fun getItem(p0: Int): Any {
        return hourList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return hourList.size
    }
}