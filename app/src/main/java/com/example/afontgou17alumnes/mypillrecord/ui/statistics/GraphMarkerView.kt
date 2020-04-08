package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.content.Context
import android.widget.TextView
import com.example.afontgou17alumnes.mypillrecord.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.time.LocalDate

class GraphMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {

    val value: TextView = findViewById(R.id.markerview_value)
    val date : TextView = findViewById(R.id.markerview_date)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        value.text = e!!.y.toString()

        //Today is 0
        val today = LocalDate.now()
        val day = today.minusDays(0 - e.x.toInt().toLong())
        date.text = day.toString()
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}