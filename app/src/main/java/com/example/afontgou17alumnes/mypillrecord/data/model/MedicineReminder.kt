package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate
import java.time.LocalTime

open class MedicineReminder(
    //Potser alguns paràmetres es podrien agrupar a una classe Medicine
    val name: String,
    val dose: Int,
    val doseUnit: String,
    val date: LocalDate,
    val time: LocalTime
) : Reminder {
}