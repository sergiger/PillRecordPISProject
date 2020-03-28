package com.example.afontgou17alumnes.mypillrecord.ui.statistics

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
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                refreshGraph()
            }

        }
        add_button.setOnClickListener {
            val mDialog = AddMeasurementDialog()
            mDialog.show(fragmentManager, "Add measurement")
            refreshGraph()
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


        //Viewport
        graph.setVisibleXRangeMaximum(8F)
        graph.moveViewToX(30F)

    }

    fun refreshGraph(){
        //Set other data
        val values = ArrayList<Entry>()
        values.add(Entry(12F, 90F))
        values.add(Entry(13F, 91F))
        values.add(Entry(14F, 90F))
        values.add(Entry(15F, 89F))
        values.add(Entry(16F, 87F))
        values.add(Entry(17F, 88F))
        values.add(Entry(18F, 85F))
        values.add(Entry(19F, 83F))
        values.add(Entry(20F, 81F))
        values.add(Entry(21F, 87F))
        values.add(Entry(22F, 82F))
        values.add(Entry(23F, 87F))
        values.add(Entry(24F, 85f))
        values.add(Entry(25F, 84F))
        values.add(Entry(26F, 83F))
        values.add(Entry(27F, 86F))
        values.add(Entry(28F, 85F))
        values.add(Entry(29F, 90F))
        values.add(Entry(30F, 91F))


        val setComp1 = LineDataSet(values, spinner.selectedItem.toString())
        setComp1.axisDependency = YAxis.AxisDependency.LEFT;
        setComp1.color = Color.rgb(250,0,0)
        setComp1.lineWidth = 3f
        setComp1.circleRadius = 6f
        setComp1.setCircleColor(Color.rgb(250,0,0))
        setComp1.highLightColor = Color.BLUE
        setComp1.setDrawValues(false)


        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(setComp1)
        val data = LineData(dataSets)
        graph.data = data
        graph.invalidate()
    }

}
