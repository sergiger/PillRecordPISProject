package com.example.afontgou17alumnes.mypillrecord.data.model.fakeStatistics

import com.example.afontgou17alumnes.mypillrecord.data.model.StatisticEntry
import java.time.LocalDate

class FakeStatisticEntry(
    val value: Float,
    val date: String) {

    fun createRealStatisticEntry(): StatisticEntry {
        return StatisticEntry(
            value,
            LocalDate.parse(date)
        )
    }
}