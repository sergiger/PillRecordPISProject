package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import org.w3c.dom.Text
import java.time.LocalDate
import kotlin.math.roundToInt

class DeleteMeasurementDialog(
    val type : String,
    val value : Float,
    val date : LocalDate
) : DialogFragment() {

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
        val view = inflater.inflate(R.layout.delete_measurement_dialog, null)
        //View elements
        val typeView = view.findViewById<TextView>(R.id.delete_measure_type)
        val valueView = view.findViewById<TextView>(R.id.delete_measure_value)
        val dateView = view.findViewById<TextView>(R.id.delete_measure_date)
        val deleteBtn = view.findViewById<Button>(R.id.OK_delete_measure_dialog)
        val cancelBtn = view.findViewById<Button>(R.id.cancel_delete_measure_dialog)

        //Set info
        typeView.text = type
        valueView.text = value.toString()
        dateView.text = date.toString()

        //Set listeners
        deleteBtn.setOnClickListener {
            Controller.deleteMeasure(type, value, date)
            dismiss()
        }
        cancelBtn.setOnClickListener {
            dismiss()
        }
        return view
    }
}