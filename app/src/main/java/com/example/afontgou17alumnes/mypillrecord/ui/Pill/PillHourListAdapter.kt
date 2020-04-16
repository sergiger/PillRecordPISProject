package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.app.Activity
import android.content.Context

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.Reminder

//Hi ha dos classes perquè tràcta el mateix tipus de llista
class PillHourListAdapter (val activity: Fragment, val reminderList : Array<String>) : BaseAdapter() {
    val view1:View = View.inflate(activity.context, R.layout.pill_frequency_list, null)

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity.context, R.layout.pill_frequency_list, null)
        val infoTextView : TextView = view.findViewById(R.id.tw_hour)
        infoTextView.text = reminderList[p0].toString()
        Log.e("ESTIC DINS", "getView")
        return view
    }

    override fun getItem(p0: Int): Any {
        return reminderList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return reminderList.size;
    }
}
//hores PillMedication
class PillHourListAdapter2 (val activity: Context, val reminderList : Array<String>,val classe : PillMedication) : BaseAdapter() {
    val view1:View = View.inflate(activity, R.layout.pill_frequency_list, null)

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity, R.layout.pill_frequency_list, null)
        val infoTextView : TextView = view.findViewById(R.id.tw_hour)
        infoTextView.text = reminderList[p0].toString()
        val button : Button = view.findViewById(R.id.btn_delete_button)
        Log.e("ESTIC DINS", "getView")
        if(reminderList.size== 1){
            button.visibility = View.INVISIBLE
        }
        else{
            button.setOnClickListener {
                var w_hourListfrequency= classe.get_w_hourListfrequency()
                w_hourListfrequency.removeAt(p0)
                classe.listHasChanged(w_hourListfrequency)
            }
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
        return reminderList.size;
    }
}

//dies Fragment 4
class PillHourListAdapter3 (val activity: Context, val reminderList : Array<String>,val classe : Pillfrequency_FragmentFour) : BaseAdapter() {
    val view1:View = View.inflate(activity, R.layout.pill_frequency_list, null)

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity, R.layout.pill_frequency_list, null)
        val infoTextView : TextView = view.findViewById(R.id.tw_hour)
        infoTextView.text = reminderList[p0].toString()
        val button : Button = view.findViewById(R.id.btn_delete_button)
        Log.e("ESTIC DINS", "getView")
        if(reminderList.size== 1){
            button.visibility = View.INVISIBLE
        }
        else{
            button.setOnClickListener {
                var w_hourListfrequency= classe.get_w_dayListfrequency()
                w_hourListfrequency.removeAt(p0)
                classe.listHasChanged(w_hourListfrequency)

                val dies = (activity as PillFrequency?)?.getDiesSaltejats()
                dies!!.removeAt(p0)
                (activity as PillFrequency?)?.setDiesSaltejats(dies)
            }
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
        return reminderList.size;
    }
}
//hores PillMesurements
class PillHourListAdapter4 (val activity: Context, val reminderList : Array<String>,val classe : Pill_mesurements_info) : BaseAdapter() {
    val view1:View = View.inflate(activity, R.layout.pill_frequency_list, null)

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity, R.layout.pill_frequency_list, null)
        val infoTextView : TextView = view.findViewById(R.id.tw_hour)
        infoTextView.text = reminderList[p0].toString()
        val button : Button = view.findViewById(R.id.btn_delete_button)
        Log.e("ESTIC DINS", "getView")
        if(reminderList.size== 1){
            button.visibility = View.INVISIBLE
        }
        else{
            button.setOnClickListener {
                var w_hourListfrequency= classe.get_w_hourListfrequency()
                w_hourListfrequency.removeAt(p0)
                classe.listHasChanged(w_hourListfrequency)
            }
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
        return reminderList.size;
    }
}
//hores PillSports
class PillHourListAdapter5 (val activity: Context, val reminderList : Array<String>,val classe : PillSports) : BaseAdapter() {
    val view1:View = View.inflate(activity, R.layout.pill_frequency_list, null)

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = View.inflate(activity, R.layout.pill_frequency_list, null)
        val infoTextView : TextView = view.findViewById(R.id.tw_hour)
        infoTextView.text = reminderList[p0].toString()
        val button : Button = view.findViewById(R.id.btn_delete_button)
        Log.e("ESTIC DINS", "getView")
        if(reminderList.size== 1){
            button.visibility = View.INVISIBLE
        }
        else{
            button.setOnClickListener {
                var w_hourListfrequency= classe.get_w_hourListfrequency()
                w_hourListfrequency.removeAt(p0)
                classe.listHasChanged(w_hourListfrequency)
            }
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
        return reminderList.size;
    }
}