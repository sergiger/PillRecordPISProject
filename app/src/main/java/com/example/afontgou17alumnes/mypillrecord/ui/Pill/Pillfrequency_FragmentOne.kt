package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.ReminderListAdapter
import kotlinx.android.synthetic.main.activity_add_unplanned_medicine.*
import kotlinx.android.synthetic.main.fragment_pillfrequency__one.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import kotlinx.android.synthetic.main.time_dialog.view.*
import kotlinx.android.synthetic.main.time_dialog.view.OK
import kotlinx.android.synthetic.main.time_dialog.view.cancel
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Pillfrequency_FragmentOne : Fragment() {
    var hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    var minute=Calendar.getInstance().get(Calendar.MINUTE)
    var timeListViewfrequency : ListView? = null
    var timeAdapter : PillHourListAdapter? = null
    var w_hourListfrequency= mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pillfrequency__one, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //createHourList()
        btn_add_field_button.setOnClickListener {
            select_time()
        }
    }

    private fun listHasChanged( llista: MutableList<String>){
        val arraylocal = llista.toTypedArray()
        val timeListViewfrequency2: ListView? = view?.findViewById(R.id.lw_hours)
        val timeAdapter2: PillHourListAdapter = PillHourListAdapter(this, arraylocal)
        if (timeListViewfrequency2 != null) {
            timeListViewfrequency2.adapter = timeAdapter2
        }
        timeListViewfrequency = timeListViewfrequency2
        Log.e("timeListViewfrequency", timeListViewfrequency.toString())
        timeAdapter = timeAdapter2
        Toast.makeText(activity, "Added", LENGTH_LONG).show()
        timeListViewfrequency?.setOnItemClickListener { adapterView, view, i, l ->
            Log.e("POSITION", timeAdapter?.getItem(i).toString())
            var button: Button = view.findViewById(R.id.btn_delete_button)
            if( button.visibility ==View.INVISIBLE){
                button.visibility=View.VISIBLE
                button.setOnClickListener {
                    llista.removeAt(i)
                    listHasChanged(llista)
                }
            }
            else{
                button.visibility=View.INVISIBLE
            }
        }
    }

    /*
    private fun createHourList() {
        timeListViewfrequency?.setOnItemClickListener { adapterView, view, i, l ->
            Log.e("POSITION", timeAdapter?.getItem(i).toString())
        }
        Log.e("arrayOf",hourListfrequency.toString())
        w_hourListfrequency=hourListfrequency.toMutableList()
        Log.e("toMutableList",w_hourListfrequency.toString())
    }
     */

    fun select_time(){
        var new_Hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        var new_minute= Calendar.getInstance().get(Calendar.MINUTE)
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.time_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(activity!!)
            .setView(mDialogView)
            .setTitle("Set date")
        val mAlertDialog = mBuilder.show()
        mDialogView.time_Picker.setOnTimeChangedListener { view, hour, minute ->
            new_Hour=hour
            new_minute=minute
        }
        mDialogView.OK.setOnClickListener {
            this.hour=new_Hour
            this.minute=new_minute
            var min=this.minute.toString()
            var hou=this.hour.toString()
            if(this.minute<10){
                min="0"+this.minute.toString()
            }
            if(this.hour<10){
                hou="0"+this.hour.toString()
            }
            //Aqui és on s'ha de posar on vols que s'escrigui el temps
            val pos = w_hourListfrequency.size
            if (w_hourListfrequency.contains(hou+":"+min)){
                Toast.makeText(activity, "Time " +hou+":"+min +" alredy added", LENGTH_LONG).show()
            }
            else {
                w_hourListfrequency.add(pos, hou + ":" + min)
                //la llista ha canviat
                listHasChanged(w_hourListfrequency)
                mAlertDialog.dismiss()
            }
        }
        mDialogView.cancel.setOnClickListener {
            Toast.makeText(activity, "Cancelled", LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
    }

}
