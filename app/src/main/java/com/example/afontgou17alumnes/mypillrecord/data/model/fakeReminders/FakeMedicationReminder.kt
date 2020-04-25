package com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders

import com.example.afontgou17alumnes.mypillrecord.data.model.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.ReminderStatus
import java.time.LocalDate
import java.time.LocalTime

class FakeMedicationReminder(
    val name: String,
    val dose: Int,
    val doseUnit: String,
    override var date: String,
    override var time: String,
    override var done: ReminderStatus=ReminderStatus.TO_DO
):FakeReminder {
    override fun createRealReminder(): com.example.afontgou17alumnes.mypillrecord.data.model.Reminder {
        return MedicineReminder(name,dose,doseUnit, LocalDate.parse(date), LocalTime.parse(time),done)
    }

}