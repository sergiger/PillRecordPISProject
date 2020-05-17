package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller

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

class followingAdapter (val activity: Context, val reminderList : Array<String>,val classe : Team_fragment) : BaseAdapter() {
    val view1:View = View.inflate(activity, R.layout.agenda_person_item_list, null)

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity, R.layout.agenda_person_item_list, null)
        val infoTextView : TextView = view.findViewById(R.id.tv_person_type)
        infoTextView.text = reminderList[p0].toString()
        val button : Button = view.findViewById(R.id.btn_stopSharing)
        //Log.e("ESTIC DINS", "getView")
        button.text="STOP FOLLOWING"
        button.setOnClickListener {
            Controller.deletefollowing(reminderList[p0].toString(),Controller.SbShareToMe[reminderList[p0].toString()]!!)
            Controller.SbShareToMe.remove(reminderList[p0].toString())
            classe.listHasChanged(Controller.SbShareToMe.keys.toMutableList())
        }
        infoTextView.setOnClickListener{
            classe.InicializateLayout(reminderList[p0].toString())
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