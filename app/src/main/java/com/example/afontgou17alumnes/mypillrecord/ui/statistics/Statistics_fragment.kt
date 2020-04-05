package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_add_unplanned_medicine.*
import kotlinx.android.synthetic.main.add_measure_dialog.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import kotlinx.android.synthetic.main.statistics_fragment_fragment.*
import kotlinx.android.synthetic.main.time_dialog.view.*
import java.util.*


class Statistics_fragment : Fragment() {

    companion object {
        fun newInstance() =
            Statistics_fragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.statistics_fragment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createGraph()
        Controller.setStatisticsData()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                refreshGraph(p2)
            }

        }
        add_button.setOnClickListener {
            val mDialog = AddMeasurementDialog()
            mDialog.show(fragmentManager, "Add measurement")
        }

    }

    fun createGraph(){
        val graph = view!!.findViewById<LineChart>(R.id.graph)
        graph.setTouchEnabled(true)
        graph.setPinchZoom(true)
        graph.description.isEnabled = false
        graph.setDrawBorders(true)
        graph.setBorderColor(Color.BLACK)
        graph.setBorderWidth(1f)

        //Formatting xAxis
        graph.xAxis.valueFormatter = DayValueFormatter()
        graph.xAxis.granularity = 1f
        graph.xAxis.position = XAxis.XAxisPosition.BOTTOM
        graph.xAxis.textSize = 15f
        graph.xAxis.labelRotationAngle = 270F


        //Formatting yAxis
        graph.axisLeft.textSize = 15f
        graph.axisRight.textSize = 15f

        //Formatting legend
        graph.legend.textSize = 15f
        graph.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        graph.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
    }

    fun refreshGraph(selected: Int){
        graph.data = Controller.getGraphLineData(selected)
        graph.invalidate()

        //Viewport
        graph.setVisibleXRangeMaximum(8F)
        graph.moveViewToX(0F)
    }

}
