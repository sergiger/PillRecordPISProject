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
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import kotlinx.android.synthetic.main.add_measure_dialog.*
import kotlinx.android.synthetic.main.add_measure_dialog.view.*
import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.roundToInt

class AddMeasurementDialog : DialogFragment() {
    var value:Float=Controller.user.weight
    var date:LocalDate= LocalDate.now()
    var time:LocalTime = LocalTime.now()
    var unit="Kg"
    var type:String="Weight"
    override fun onResume() {
        super.onResume()
        val dm = DisplayMetrics()
        dialog.window!!.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels*0.9
        val height = dm.heightPixels*0.6
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
            if(enter_value.text.toString()!=""){
                addData()
                dismiss()
            }
        }
        cancel.setOnClickListener {
            dismiss()
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                    0 ->{
                        unit.text = "kg"
                        setType(0)}//Weight
                    1 -> {
                        unit.text = "bpm"
                        setType(1)}//Heart rate
                    2 -> {
                        unit.text = "mmHg"
                        setType(2)}//Arterial pressure
                    3 -> {
                        unit.text = "mg/dl"
                        setType(3)}//Glucose (before eating)
                    4 -> {
                        unit.text = "mg/dl"
                        setType(4)} //Glucose (after eating)
                    5 -> {
                        unit.text = "ºC"
                        setType(5)} //Temperature
                }
            }

        }

        return view
    }

    fun addData(){
        //TODO funció per guardar les dades cal fer que et demani la hora exacta tmb
        //Tampbé cal pensar en com fer que els reminders ja estiguin complerts

        setValues()
        var reminder = Controller.createMeasurementReminder(type,unit,date,time)
        Controller.addReminder(reminder)
        //Enviar valor on calgui
    }
    fun setType(id:Int){
        when(id){
            0->this.type="Weight"
            1->this.type="Heart rate"
            2->this.type="Arterial preasure"
            3->this.type="Glucose before eating"
            4->this.type="Glucose after eating"
            5->this.type="Temperature"
        }
    }
    fun setValues(){
        this.value=enter_value.text.toString().toFloat()
        this.unit=enter_value.unit.text.toString()
    }
}