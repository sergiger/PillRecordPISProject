package com.example.afontgou17alumnes.mypillrecord.ui.calendar

import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.BasicMedicineReminder

class MedicineListAdapter (
    val activity: Fragment,
    val medicineList : Array<BasicMedicineReminder>
) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity.context, R.layout.medicine_list, null)
        //val imageView = view.findViewById<>(R.id.medicine_image)
        val nameTextView : TextView = view.findViewById(R.id.medicine_name)
        val doseTextView : TextView = view.findViewById(R.id.medicine_dose)
        val timeTextView : TextView = view.findViewById(R.id.medicine_time)

        nameTextView.setText(medicineList[p0].id)
        doseTextView.setText(medicineList[p0].dose.toString())
        timeTextView.setText(medicineList[p0].hour.toString())

        return view;
    }

    override fun getItem(p0: Int): Any {
        return 1;
    }

    override fun getItemId(p0: Int): Long {
        return 0;
    }

    override fun getCount(): Int {
        return medicineList.size;
    }
}