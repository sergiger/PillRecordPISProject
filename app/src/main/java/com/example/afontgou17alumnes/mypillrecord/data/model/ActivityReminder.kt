package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate
import java.time.LocalTime

class ActivityReminder(
    val name: String,
    val duration: String,
    val date: LocalDate,
    val time: LocalTime
) : Reminder {
}