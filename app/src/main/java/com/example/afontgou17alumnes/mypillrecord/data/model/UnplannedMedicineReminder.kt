package com.example.afontgou17alumnes.mypillrecord.data.model

import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MedicineReminder
import java.time.LocalDate
import java.time.LocalTime

class UnplannedMedicineReminder(
    name: String,
    dose: Int,
    doseUnit: String) :
    MedicineReminder(name, dose, doseUnit, LocalDate.now(), LocalTime.of(15,0)) {
    override fun toStringPDF(): String {
        TODO("Not yet implemented")
    }
}

