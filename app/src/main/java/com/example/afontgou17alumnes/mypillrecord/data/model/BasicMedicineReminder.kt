package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalTime

data class BasicMedicineReminder (
    val id: String,
    val dose: Int,
    val hour: LocalTime
    )