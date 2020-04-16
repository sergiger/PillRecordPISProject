package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import kotlinx.android.synthetic.main.statistics_fragment_fragment.*
import java.time.LocalDate


class Statistics_fragment : Fragment(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    var shown:Int=0

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
        var start = arguments?.getInt("start")
        createGraph()
        Controller.setStatisticsData()
        if(start!=null){
            shown=start
            spinner.setSelection(shown)
        }
        refreshGraph(shown)
        Log.d("hola",shown.toString())
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                shown=p2
                refreshGraph(p2)
            }

        }
        add_button.setOnClickListener {
            val mDialog = AddMeasurementDialog()
            val b=Bundle()
            b.putInt("tius de mesurament",shown)
            mDialog.arguments=b
            mDialog.show(childFragmentManager, "Add measurement")
        }
        delete_btn_statistics.setOnClickListener {
            val h = graph.highlighted ?: return@setOnClickListener
            var type = spinner.selectedItem.toString()
            if(spinner.selectedItemPosition == 3){
                if(h[0].dataSetIndex == 0) type = "Glucose (before eating)"
                else type = "Glucose (after eating)"
            }
            val mDialog = DeleteMeasurementDialog(shown,type, h[0].y, LocalDate.now().minusDays(0 - h[0].x.toLong()))
            mDialog.show(childFragmentManager, "Delete measurement")
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

        //Setting MarkerView
        graph.marker = this.context?.let { GraphMarkerView(it, R.layout.markerview) }
    }

    fun refreshGraph(selected: Int){
        graph.data = Controller.getGraphLineData(selected)
        graph.invalidate()

        //Viewport
        graph.setVisibleXRangeMaximum(8F)
        graph.moveViewToX(0F)
    }



}


