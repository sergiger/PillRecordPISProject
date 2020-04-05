package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate
import java.time.LocalTime

class MeasurementReminder(
    val name: String,
    val unit: String,
    val date: LocalDate,
    val time: LocalTime
): Reminder {
}