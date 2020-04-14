package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate
import java.time.LocalTime

class UnplannedMedicineReminder(
    name: String,
    dose: Int,
    doseUnit: String) :
    MedicineReminder(name, dose, doseUnit, LocalDate.now(), LocalTime.of(15,0))

