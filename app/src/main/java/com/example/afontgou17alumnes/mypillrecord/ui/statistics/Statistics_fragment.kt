package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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


class Statistics_fragment : Fragment(),View.OnClickListener, AddMeasurementDialog.DialogListener,
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
        createGraph()
        Controller.setStatisticsData()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                shown=p2
                refreshGraph(p2)
            }

        }
        var sad=this
        add_button.setOnClickListener {
            onPause()
            val mDialog = AddMeasurementDialog()
            val b=Bundle()
            b.putInt("tius de mesurament",shown)
            mDialog.arguments=b
            mDialog.show(childFragmentManager, "Add measurement")
            //while(!Controller.check_Statistics_Actualizated){}
            //Controller.check_Statistics_Actualizated=false
            onResume()
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
    override fun onResume() {
        super.onResume()
        refreshGraph(shown)
    }

    override fun onClick(v: View?) {
        refreshGraph(shown)
    }

    override fun onFinishEditDialog() {
        refreshGraph(shown)
    }

}


