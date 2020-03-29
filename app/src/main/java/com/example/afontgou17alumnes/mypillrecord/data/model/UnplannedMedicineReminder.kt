package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalTime

data class UnplannedMedicineReminder (
    val id: String,
    val dose: Int,
    val doseUnit: String,
    val hour: LocalTime
    )