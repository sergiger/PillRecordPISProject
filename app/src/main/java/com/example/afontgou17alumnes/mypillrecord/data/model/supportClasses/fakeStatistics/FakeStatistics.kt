package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeStatistics

import com.example.afontgou17alumnes.mypillrecord.data.model.statistics.Statistics
import com.google.gson.Gson

class FakeStatistics {
    //Aquesta classe conté llistes de strings, aquests strings son JSON Strings de FakeStatisticsEntris
    val weightData = ArrayList<String>()
    val heartRateData = ArrayList<String>()
    val arterialPressureData = ArrayList<String>()
    val glucoseBeforeData = ArrayList<String>()
    val glucoseAfterData = ArrayList<String>()
    val temperatureData = ArrayList<String>()

    fun createRealStatistics(): Statistics {
        var statistics: Statistics =
            Statistics()
        val gson = Gson()
        //Ara he de fer el parcel de les llistes de strings i convertirles a llistes de StatisticsEntry
        if(weightData!=null){
            for(entris in weightData){
                val newEntry: FakeStatisticEntry = gson.fromJson(entris, FakeStatisticEntry::class.java)
                statistics.weightData.add(newEntry.createRealStatisticEntry())
            }
        }
        if(heartRateData!=null){
            for(entris in heartRateData){
                val newEntry: FakeStatisticEntry = gson.fromJson(entris, FakeStatisticEntry::class.java)
                statistics.heartRateData.add(newEntry.createRealStatisticEntry())
            }
        }
        if(arterialPressureData!=null){
            for(entris in arterialPressureData){
                val newEntry: FakeStatisticEntry = gson.fromJson(entris, FakeStatisticEntry::class.java)
                statistics.arterialPressureData.add(newEntry.createRealStatisticEntry())
            }
        }
        if(glucoseBeforeData!=null){
            for(entris in glucoseBeforeData){
                val newEntry: FakeStatisticEntry = gson.fromJson(entris, FakeStatisticEntry::class.java)
                statistics.glucoseBeforeData.add(newEntry.createRealStatisticEntry())
            }
        }
        if(glucoseAfterData!=null){
            for(entris in glucoseAfterData){
                val newEntry: FakeStatisticEntry = gson.fromJson(entris, FakeStatisticEntry::class.java)
                statistics.glucoseAfterData.add(newEntry.createRealStatisticEntry())
            }
        }
        if(temperatureData!=null){
            for(entris in temperatureData){
                val newEntry: FakeStatisticEntry = gson.fromJson(entris, FakeStatisticEntry::class.java)
                statistics.temperatureData.add(newEntry.createRealStatisticEntry())
            }
        }

        return statistics
    }
}