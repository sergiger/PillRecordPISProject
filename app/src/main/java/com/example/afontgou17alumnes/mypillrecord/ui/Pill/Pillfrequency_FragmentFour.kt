package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.fragment_pillfrequency__four.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Pillfrequency_FragmentFour : Fragment() {
    var timeListViewfrequency : ListView? = null
    var timeAdapter : PillHourListAdapter3? = null
    var w_dayListfrequency= mutableListOf<String>()
    var day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var month=Calendar.getInstance().get(Calendar.MONTH)+1
    var year=Calendar.getInstance().get(Calendar.YEAR)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pillfrequency__four, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //createHourList()
        btn_add_day_button.setOnClickListener {
            select_date()
        }
        val pos = w_dayListfrequency.size
        w_dayListfrequency.add(pos, day.toString()+"/"+month.toString()+"/"+year.toString())
        //la llista ha canviat
        listHasChanged(w_dayListfrequency)
    }

//per afegir
    fun select_date(){
        var new_day= Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        var new_month= Calendar.getInstance().get(Calendar.MONTH)+1
        var new_year= Calendar.getInstance().get(Calendar.YEAR)

        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.date_dialoge, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(activity!!)
            .setView(mDialogView)
            .setTitle("Set specific dates")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //Aqui guardo la data INI seleccionada
        //i també poso la data correcta al DatePicker de INICI amb la data d'avui
        val datePicker_ini = mDialogView.findViewById<DatePicker>(R.id.date_Picker)
        val today = Calendar.getInstance()
        datePicker_ini.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            new_day=day
            new_month=month
            new_year=year
            //val msg = "You Selected: $day/$month/$year"
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        //Aqui faig els listeners dels dos botons
        mDialogView.OK.setOnClickListener {
            //Toast.makeText(context,"work in progress", Toast.LENGTH_LONG).show()
            val pos = w_dayListfrequency.size
            if (w_dayListfrequency.contains(new_day.toString()+"/"+new_month.toString()+"/"+new_year.toString())){
                Toast.makeText(activity, "Day " +new_day.toString()+"/"+new_month.toString()+"/"+new_year.toString() +" alredy added", Toast.LENGTH_LONG).show()
            }
            else {
                w_dayListfrequency.add(pos, new_day.toString()+"/"+new_month.toString()+"/"+new_year.toString())
                //la llista ha canviat
                listHasChanged(w_dayListfrequency)
                mAlertDialog.dismiss()
            }
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(context,"Cancel", Toast.LENGTH_SHORT).show()
        }
    }
//per modificar
    fun select_date2(textView: TextView){
        var new_day= Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        var new_month= Calendar.getInstance().get(Calendar.MONTH)+1
        var new_year= Calendar.getInstance().get(Calendar.YEAR)

        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.date_dialoge, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(activity!!)
            .setView(mDialogView)
            .setTitle("Set specific dates")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //Aqui guardo la data INI seleccionada
        //i també poso la data correcta al DatePicker de INICI amb la data d'avui
        val datePicker_ini = mDialogView.findViewById<DatePicker>(R.id.date_Picker)
        val today = Calendar.getInstance()
        datePicker_ini.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            new_day=day
            new_month=month
            new_year=year
            //val msg = "You Selected: $day/$month/$year"
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        //Aqui faig els listeners dels dos botons
        mDialogView.OK.setOnClickListener {
            val pos = w_dayListfrequency.indexOf(textView.text)
            if (w_dayListfrequency.contains(new_day.toString()+"/"+new_month.toString()+"/"+new_year.toString())){
                Toast.makeText(activity, "Day " +new_day.toString()+"/"+new_month.toString()+"/"+new_year.toString() +" alredy added", Toast.LENGTH_LONG).show()
            }
            else {
                w_dayListfrequency.set(pos, new_day.toString()+"/"+new_month.toString()+"/"+new_year.toString())
                textView.text= new_day.toString()+"/"+new_month.toString()+"/"+new_year.toString()
                //la llista ha canviat
                //listHasChanged(w_hourListfrequency)
                mAlertDialog.dismiss()
            }
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(context,"Cancel", Toast.LENGTH_SHORT).show()
        }
    }

    fun listHasChanged( llista: MutableList<String>){
        val arraylocal = llista.toTypedArray()
        val timeListViewfrequency3: ListView? = view?.findViewById(R.id.lw_days)
        val timeAdapter3: PillHourListAdapter3 = PillHourListAdapter3(context!!, arraylocal,this)
        if (timeListViewfrequency3 != null) {
            timeListViewfrequency3.adapter = timeAdapter3
        }
        timeListViewfrequency = timeListViewfrequency3
        Log.e("timeListViewfrequency", timeListViewfrequency.toString())
        timeAdapter = timeAdapter3
        if(llista.size > 1){
            Toast.makeText(activity, "Added", Toast.LENGTH_LONG).show()
        }
        timeListViewfrequency?.setOnItemClickListener { adapterView, view, i, l ->
            Log.e("POSITION", timeAdapter?.getItem(i).toString())
            var text : TextView = view.findViewById(R.id.tw_hour)
            select_date2(text)
        }
    }
    fun get_w_dayListfrequency(): MutableList<String> {
        return w_dayListfrequency
    }


}
