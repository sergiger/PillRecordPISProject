package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.widget.Spinner
import com.example.afontgou17alumnes.mypillrecord.R
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class YAxisFormatter(val spinnerItemId: Int) : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        var unit : String = ""
        when(spinnerItemId){
            0 -> unit = "kg"
            1 -> unit = "kg"
            2 -> unit = "kg"
            3 -> unit = "kg"
            4 -> unit = "kg"
            else-> ""
        }
        return value.toString()
    }
}