package com.example.afontgou17alumnes.mypillrecord.data.controller

import android.graphics.Color
import com.example.afontgou17alumnes.mypillrecord.data.model.*
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.statistics_fragment_fragment.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.ArrayList

object Controller {
    val user = User("user@gmail.com", "PillRecord", "123", "Male", 19, 50F, 160F)
    val statistics = Statistics()

    fun setStatisticsData(){
        val data1 = arrayOf<StatisticEntry>(
            StatisticEntry(80F, LocalDate.of(2020, 3, 12)),
            StatisticEntry(81F, LocalDate.of(2020, 3, 13)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 14)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 15)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 16)),
            StatisticEntry(81F, LocalDate.of(2020, 3, 17)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 18)),
            StatisticEntry(84F, LocalDate.of(2020, 3, 19)),
            StatisticEntry(86F, LocalDate.of(2020, 3, 20)),
            StatisticEntry(85F, LocalDate.of(2020, 3, 21)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 22)),
            StatisticEntry(81F, LocalDate.of(2020, 3, 23)),
            StatisticEntry(84F, LocalDate.of(2020, 3, 24)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 25)),
            StatisticEntry(85F, LocalDate.of(2020, 3, 26)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 27)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 28)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 29)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 30)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 31)),
            StatisticEntry(84F, LocalDate.of(2020, 4, 1)),
            StatisticEntry(81F, LocalDate.of(2020, 4, 2)),
            StatisticEntry(85F, LocalDate.of(2020, 4, 3))
        )

        val data2 = arrayOf<StatisticEntry>(
            StatisticEntry(85F, LocalDate.of(2020, 3, 12)),
            StatisticEntry(86F, LocalDate.of(2020, 3, 13)),
            StatisticEntry(84F, LocalDate.of(2020, 3, 14)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 15)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 16)),
            StatisticEntry(81F, LocalDate.of(2020, 3, 17)),
            StatisticEntry(89F, LocalDate.of(2020, 3, 18)),
            StatisticEntry(87F, LocalDate.of(2020, 3, 19)),
            StatisticEntry(85F, LocalDate.of(2020, 3, 20)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 21)),
            StatisticEntry(84F, LocalDate.of(2020, 3, 22)),
            StatisticEntry(89F, LocalDate.of(2020, 3, 23)),
            StatisticEntry(86F, LocalDate.of(2020, 3, 24)),
            StatisticEntry(80F, LocalDate.of(2020, 3, 25)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 26)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 27)),
            StatisticEntry(88F, LocalDate.of(2020, 3, 28)),
            StatisticEntry(84F, LocalDate.of(2020, 3, 29)),
            StatisticEntry(89F, LocalDate.of(2020, 3, 30)),
            StatisticEntry(87F, LocalDate.of(2020, 3, 31)),
            StatisticEntry(85F, LocalDate.of(2020, 4, 1)),
            StatisticEntry(86F, LocalDate.of(2020, 4, 2)),
            StatisticEntry(83F, LocalDate.of(2020, 4, 3))
        )
        statistics.weightData.addAll(data1)
        statistics.heartRateData.addAll(data2)
        statistics.arterialPressureData.addAll(data1)
        statistics.glucoseAfterData.addAll(data2)
        statistics.glucoseBeforeData.addAll(data1)
        statistics.temperatureData.addAll(data2)
    }

    fun getGraphLineData(select: Int) : LineData {
        //Agafem les dades que ens interessen segons quin grafic muntarem
        var values = mutableListOf<StatisticEntry>()
        var label = "Default"
        when(select){
            0->{
                values = statistics.weightData
                label = "Weight" }
            1 ->{ values = statistics.heartRateData
                label = "Heart rate"}
            2 ->{ values = statistics.arterialPressureData
                label = "Arterial pressure"}
            3 ->{ values = statistics.glucoseBeforeData
                label = "Glucose"}
            4 -> {values = statistics.temperatureData
                label = "Temperature"}
        }

        //Formatejem les dades per a que les pugui llegir el gràfic
        val formattedValues = ArrayList<Entry>()
        for (i in values){
            val days = ChronoUnit.DAYS.between(i.date,LocalDate.now())
            formattedValues.add(Entry(0 - days.toFloat(), i.value))
        }

        val setComp1 = LineDataSet(formattedValues, label)
        setComp1.axisDependency = YAxis.AxisDependency.LEFT;
        setComp1.color = Color.rgb(250,0,0)
        setComp1.lineWidth = 3f
        setComp1.circleRadius = 6f
        setComp1.setCircleColor(Color.rgb(250,0,0))
        setComp1.highLightColor = Color.RED
        setComp1.setDrawValues(false)

        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(setComp1)
        return LineData(dataSets)
    }

    fun setGender(gender:String){
        user.gender=gender
    }
    fun setHeihght(height:Float){
        user.height=height
    }
    fun setWeight(weight:Float){
        user.weight=weight
    }
    fun setPasword(oldPasword:String,newPasword:String,repetedNewPasword:String){
        //S'ha de millorar el metode change pasword per a que pugui enviar errors
        user.changePasword(oldPasword,newPasword,repetedNewPasword)
    }
    fun addTherapy(therapy: Therapy){
        user.addTherapy(therapy)
    }
    fun addReminder(reminder: Reminder){
        user.addReminder(reminder)
    }
    fun createMedicineReminder(medicine:String,dose:Int,units:String,
    date:LocalDate,time: LocalTime
    ):Reminder{
        return MedicineReminder(medicine,dose,units,date,time)
    }

    fun createActivityReminder(activity: String, duration: Int,
                               date: LocalDate, time: LocalTime
    ): Reminder {
        return ActivityReminder(activity,duration,date,time)
    }
    fun createMeasurementReminder(type:String,units:String,value:Float,date:LocalDate,time:LocalTime):Reminder{
        return MeasurementReminder(type,units,value,date,time)
    }
}