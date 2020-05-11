package com.example.afontgou17alumnes.mypillrecord.data.model.statistics

import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeStatistics.FakeStatisticEntry
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeStatistics.FakeStatistics
import com.google.gson.Gson
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
        Controller.controllerSharePrefs.sharedUpLoad()
    }
    fun createFakeStatistics(): FakeStatistics {
        var fakeStatistics= FakeStatistics()
        var gson= Gson()
        for(entry in this.weightData){
            fakeStatistics.weightData.add(gson.toJson(FakeStatisticEntry(entry.value,entry.date.toString())))
        }
        for(entry in this.arterialPressureData){
            fakeStatistics.arterialPressureData.add(gson.toJson(FakeStatisticEntry(entry.value,entry.date.toString())))
        }
        for(entry in this.glucoseAfterData){
            fakeStatistics.glucoseAfterData.add(gson.toJson(FakeStatisticEntry(entry.value,entry.date.toString())))
        }
        for(entry in this.glucoseBeforeData){
            fakeStatistics.glucoseBeforeData.add(gson.toJson(FakeStatisticEntry(entry.value,entry.date.toString())))
        }
        for(entry in this.heartRateData){
            fakeStatistics.heartRateData.add(gson.toJson(FakeStatisticEntry(entry.value,entry.date.toString())))
        }
        for(entry in this.temperatureData){
            fakeStatistics.temperatureData.add(gson.toJson(FakeStatisticEntry(entry.value,entry.date.toString())))
        }
        return fakeStatistics
    }

    fun clear() {
        this.arterialPressureData.clear()
        this.glucoseAfterData.clear()
        this.glucoseBeforeData.clear()
        this.heartRateData.clear()
        this.weightData.clear()
        this.temperatureData.clear()
    }
}