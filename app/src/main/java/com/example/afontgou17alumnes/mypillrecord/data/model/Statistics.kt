package com.example.afontgou17alumnes.mypillrecord.data.model

import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import java.time.LocalDate

class Statistics {
    val weightData = mutableListOf<StatisticEntry>()
    val heartRateData = mutableListOf<StatisticEntry>()
    val arterialPressureData = mutableListOf<StatisticEntry>()
    val glucoseBeforeData = mutableListOf<StatisticEntry>()
    val glucoseAfterData = mutableListOf<StatisticEntry>()
    val temperatureData = mutableListOf<StatisticEntry>()

    fun deleteMeasure(type: String, value: Float, date: LocalDate) {
        val array : MutableList<StatisticEntry>
        when(type){
            "Weight" -> array = weightData
            "Heart rate" -> array = heartRateData
            "Arterial pressure" -> array = arterialPressureData
            "Glucose (before eating)" -> array = glucoseBeforeData
            "Glucose (after eating)" -> array = glucoseAfterData
            "Temperature" -> array = temperatureData
            else -> array = mutableListOf()
        }

        //Search of the entry to delete
        for(entry in array){
            if (entry.value.equals(value) && entry.date == date){
                array.remove(entry)
                break
            }
        }
    }
}