package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.ui.Pill.PillFrequency
import com.example.afontgou17alumnes.mypillrecord.ui.Pill.Pillfrequency_FragmentFour
import kotlinx.android.synthetic.main.agenda_person_item_list.view.*

class personAdapter (val activity: Context, val reminderList : Array<String>,val classe : agenda_team) : BaseAdapter() {
    val view1:View = View.inflate(activity, R.layout.agenda_person_item_list, null)

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity, R.layout.agenda_person_item_list, null)
        val infoTextView : TextView = view.findViewById(R.id.tv_person_type)
        infoTextView.text = reminderList[p0].toString()
        val button : Button = view.findViewById(R.id.btn_stopSharing)
        Log.e("ESTIC DINS", "getView")
        button.setOnClickListener {
            Controller.deleteshareto(reminderList[p0].toString(),Controller.IShareTo[reminderList[p0].toString()]!!)
            Controller.IShareTo.remove(reminderList[p0].toString())
            classe.listHasChanged(Controller.IShareTo.keys.toMutableList())
        }


        return view
    }

    override fun getItem(p0: Int): Any {
        return reminderList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return reminderList.size
    }
}