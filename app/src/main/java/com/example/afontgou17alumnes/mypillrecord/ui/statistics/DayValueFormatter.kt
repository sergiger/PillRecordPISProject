package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.time.LocalDate
import java.util.*

class DayValueFormatter : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        //Today is 30
        val today = LocalDate.now()
        val day = today.minusDays(30 - value.toInt().toLong())
        return day.toString()
    }
}