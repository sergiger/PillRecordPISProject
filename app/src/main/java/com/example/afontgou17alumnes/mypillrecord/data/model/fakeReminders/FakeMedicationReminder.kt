package com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders

import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.Contacte
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
    override var done: Int=55,
    override var ID: String=""
):FakeReminder {
    override fun createRealReminder(): com.example.afontgou17alumnes.mypillrecord.data.model.Reminder {
        return MedicineReminder(name,dose,doseUnit, LocalDate.parse(date), LocalTime.parse(time),Controller.getIntToReminderStatus(done),ID)
    }

}