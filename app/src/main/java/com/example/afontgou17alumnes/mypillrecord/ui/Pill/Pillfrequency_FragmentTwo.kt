package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.os.Bundle

import android.util.Log
//import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_add_unplanned_medicine.*
import kotlinx.android.synthetic.main.number_dialog.view.*
import kotlinx.android.synthetic.main.number_dialog.view.OK
import kotlinx.android.synthetic.main.number_dialog.view.cancel
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*

/**
 * A simple [Fragment] subclass.
 */
class Pillfrequency_FragmentTwo : Fragment() {
    var dose=1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.e("RETURN", "HE ENTRAT AL RETURN")
        return inflater.inflate(R.layout.fragment_pillfrequency__two, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dose_button.setOnClickListener{
            Log.w("button", "HE ENTRAT")
            select_dose()
        }
    }


    fun select_dose(){
        var new_dose=1
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.number_dialog, null)
        //Set Number Picker
        mDialogView.number_Picker.minValue = 1
        mDialogView.number_Picker.maxValue = 100
        mDialogView.number_Picker.wrapSelectorWheel = false

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(activity!!)
            .setView(mDialogView)
            .setTitle("Set how often")
        val mAlertDialog = mBuilder.show()
        mDialogView.number_Picker.setOnValueChangedListener { picker, oldVal, newVal ->
            new_dose=newVal
        }
        mDialogView.OK.setOnClickListener {
            this.dose=new_dose
            dose_button.text=dose.toString()
            Toast.makeText(activity, "Saved", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
    }


}
