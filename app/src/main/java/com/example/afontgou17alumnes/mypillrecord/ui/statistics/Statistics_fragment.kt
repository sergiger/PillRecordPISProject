package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import com.example.afontgou17alumnes.mypillrecord.R
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
        val graph = view!!.findViewById<LineChart>(R.id.graph)
        val spinner = view!!.findViewById<Spinner>(R.id.spinner)
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

        val values = ArrayList<Entry>()
        values.add(Entry(0F, 5F))
        values.add(Entry(1F, 2F))
        values.add(Entry(2F, 3F))
        values.add(Entry(3F, 6F))
        values.add(Entry(4F, 1F))
        values.add(Entry(5F, 1F))
        values.add(Entry(6F, 1F))
        values.add(Entry(7F, 1F))
        values.add(Entry(8F, 1F))
        values.add(Entry(9F, 5F))
        values.add(Entry(10F, 2F))
        values.add(Entry(11F, 2F))
        values.add(Entry(12F, 4f))
        values.add(Entry(13F, 6F))
        values.add(Entry(14F, 1F))
        values.add(Entry(15F, 1F))
        values.add(Entry(16F, 1F))
        values.add(Entry(17F, 1F))
        values.add(Entry(18F, 1F))


        val setComp1 = LineDataSet(values, spinner.selectedItem.toString())
        setComp1.axisDependency = YAxis.AxisDependency.LEFT;
        setComp1.color = Color.rgb(78,159,230)
        setComp1.lineWidth = 3f
        setComp1.circleRadius = 6f
        setComp1.setCircleColor(Color.rgb(78,159,230))
        setComp1.highLightColor = Color.BLUE
        setComp1.setDrawValues(false)


        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(setComp1)
        val data = LineData(dataSets)
        graph.data = data

        //Viewport
        graph.setVisibleXRangeMaximum(8F)
        graph.moveViewToX(0F)

        graph.invalidate()

    }



}
