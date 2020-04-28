package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder

import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.Reminder
import java.time.LocalDate
import java.time.LocalTime

class FakeMedicationReminder(
    val name: String,
    val dose: Int,
    val doseUnit: String,
    override var date: String,
    override var time: String,
    override var done: Int=55,
    override var ID: Int=-1
):FakeReminder {
    override fun createRealReminder(): Reminder {
        return MedicineReminder(
            name,
            dose,
            doseUnit,
            LocalDate.parse(date),
            LocalTime.parse(time),
            Controller.getIntToReminderStatus(done),
            ID
        )
    }

}