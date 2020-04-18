package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.StatisticEntry
import kotlinx.android.synthetic.main.add_measure_dialog.*
import kotlinx.android.synthetic.main.statistics_fragment_fragment.*
import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.roundToInt

class AddMeasurementDialog : DialogFragment() {
    var value:Float= Controller.user.weight
    var date:LocalDate= LocalDate.now()
    var time:LocalTime = LocalTime.now()
    var unit="kg"
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
        val args=arguments
        var id:Int=0
        args?.let {
            id=it.getInt("tius de mesurament")
        }
        val view = inflater.inflate(R.layout.add_measure_dialog, null)
        //View elements
        val ok = view.findViewById<Button>(R.id.OK_add_measure_dialog)
        val cancel = view.findViewById<Button>(R.id.cancel_add_measure_dialog)
        val spinner = view.findViewById<Spinner>(R.id.spinner_measurement)
        val unit = view.findViewById<TextView>(R.id.unit)
        setType(id)
        setUnits(id,unit)
        if(id==4){
            spinner.setSelection(5)
        }else
            spinner.setSelection(id)
        ok.setOnClickListener(object :View.OnClickListener{
            override fun onClick(view:View){
                if(enter_value.text.toString() != ""){
                    addData(unit)
                    //statisticsFragment.refreshGraph(id)

                    //Aixó hauria de funcionar, però no sé per què no funciona, si algú s'ho pot mirar, jo ja no puc fer res més en aqeust
                    /*val callingActivity=activity as DialogListener?
                    callingActivity!!.onFinishEditDialog()*/
                    (activity as MainActivity).go_To_Statistics(id)
                    //Controller.check_Statistics_Actualizated=true
                    dismiss()
                }
            }
        })
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

    fun addData(unit:TextView){
        val pos = view!!.findViewById<Spinner>(R.id.spinner_measurement).selectedItemPosition
        val value = view!!.findViewById<EditText>(R.id.enter_value).text.toString().toFloat()
        val datePicker = view!!.findViewById<DatePicker>(R.id.add_measurement_date)
        //TODO funció per guardar les dades cal fer que et demani la hora exacta tmb
        //Tampbé cal pensar en com fer que els reminders ja estiguin complerts

        setValues(unit)
        var reminder = Controller.createMeasurementReminder(type,this.unit,date,time,value)
        Controller.addReminder(reminder)
        //Enviar valor on calgui

        //Part modificar Statistics
        when(pos){
            0-> Controller.statistics.weightData.add(StatisticEntry(value,
                LocalDate.of(datePicker.year,datePicker.month+1,datePicker.dayOfMonth)))
            1-> Controller.statistics.heartRateData.add(StatisticEntry(value,
                LocalDate.of(datePicker.year,datePicker.month+1,datePicker.dayOfMonth)))
            2-> Controller.statistics.arterialPressureData.add(StatisticEntry(value,
                LocalDate.of(datePicker.year,datePicker.month+1,datePicker.dayOfMonth)))
            3-> Controller.statistics.glucoseBeforeData.add(StatisticEntry(value,
                LocalDate.of(datePicker.year,datePicker.month+1,datePicker.dayOfMonth)))
            4-> Controller.statistics.glucoseAfterData.add(StatisticEntry(value,
                LocalDate.of(datePicker.year,datePicker.month+1,datePicker.dayOfMonth)))
            5-> Controller.statistics.temperatureData.add(StatisticEntry(value,
                LocalDate.of(datePicker.year,datePicker.month+1,datePicker.dayOfMonth)))
        }
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
    fun setUnits(id:Int,unit: TextView){
        when(id){
            0 ->
                unit.text = "kg"
            1 ->
                unit.text = "bpm"
            2 ->
                unit.text = "mmHg"
            3 ->
                unit.text = "mg/dl"
            4 ->
                unit.text = "mg/dl"
            5 ->
                unit.text = "ºC"
        }
    }
    fun setValues(unit:TextView){
        this.value=enter_value.text.toString().toFloat()
        this.unit=unit.text.toString()
    }

    interface DialogListener {
        fun onFinishEditDialog()
    }

}