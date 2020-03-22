package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity__register4.*
import kotlinx.android.synthetic.main.statistics_fragment_fragment.*
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
        add_button.setOnClickListener {
            val intent = Intent(activity, AddMeasurement::class.java)
            startActivity(intent)
        }

    }



    fun createGraph(){
        val calendar: Calendar = Calendar.getInstance()
        val d1: Date = calendar.time
        calendar.add(Calendar.DATE, 1)
        val d2: Date = calendar.time
        calendar.add(Calendar.DATE, 1)
        val d3: Date = calendar.time
        calendar.add(Calendar.DATE, 1)
        val d4: Date = calendar.time
        calendar.add(Calendar.DATE, 1)
        val d5: Date = calendar.time
        calendar.add(Calendar.DATE, 1)
        val d6: Date = calendar.time
        calendar.add(Calendar.DATE, 1)
        val d7: Date = calendar.time
        calendar.add(Calendar.DATE, 1)
        val d8: Date = calendar.time
        calendar.add(Calendar.DATE, 1)
        val d9: Date = calendar.time



        val graph = view?.findViewById(R.id.graph) as GraphView
        val series = LineGraphSeries(arrayOf(DataPoint(d1, 1.0), DataPoint(d2, 5.0), DataPoint(d3, 3.0)
            , DataPoint(d4, 3.0), DataPoint(d5, 3.0), DataPoint(d6, 3.0), DataPoint(d7, 3.0),
            DataPoint(d8, 3.0), DataPoint(d9, 3.0)))
        graph.addSeries(series)

        graph.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity)
        graph.gridLabelRenderer.numHorizontalLabels = 5
        graph.gridLabelRenderer.numVerticalLabels = 6
        graph.gridLabelRenderer.setHumanRounding(false)


        graph.viewport.isScrollable = true
        graph.viewport.setMaxY(5.0)
        graph.viewport.setMinY(0.0)
        graph.viewport.setMaxX(d4.time.toDouble())
        graph.viewport.setMinX(d1.time.toDouble())
        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.borderColor = Color.BLACK


        //Leyenda
        series.title = "Peso"
        graph.legendRenderer.isVisible = true
        graph.legendRenderer.align = LegendRenderer.LegendAlign.TOP
        graph.legendRenderer.backgroundColor = Color.TRANSPARENT
    }



}
