package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.add_measure_dialog.*
import kotlin.math.roundToInt

class AddMeasurementDialog : DialogFragment() {

    override fun onResume() {
        super.onResume()
        val dm = DisplayMetrics()
        dialog.window!!.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels*0.9
        val height = dm.heightPixels*0.4
        dialog.window!!.setLayout(width.roundToInt(), height.roundToInt())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_measure_dialog, null)
        //View elements
        val ok = view.findViewById<Button>(R.id.OK_add_measure_dialog)
        val cancel = view.findViewById<Button>(R.id.cancel_add_measure_dialog)
        val spinner = view.findViewById<Spinner>(R.id.spinner_measurement)
        val unit = view.findViewById<TextView>(R.id.unit)
        ok.setOnClickListener {
            addData()
            dismiss()
        }
        cancel.setOnClickListener {
            dismiss()
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                    0 -> unit.text = "kg" //Weight
                    1 -> unit.text = "bpm" //Heart rate
                    2 -> unit.text = "mmHg" //Arterial pressure
                    3 -> unit.text = "mg/dl" //Glucose (before eating)
                    4 -> unit.text = "mg/dl" //Glucose (after eating)
                    5 -> unit.text = "ºC" //Temperature
                }
            }

        }

        return view
    }

    fun addData(){
        //TODO funció per guardar les dades
        val value : Float = view!!.findViewById<EditText>(R.id.enter_value).text.toString().toFloat()
        //Enviar valor on calgui
    }
}