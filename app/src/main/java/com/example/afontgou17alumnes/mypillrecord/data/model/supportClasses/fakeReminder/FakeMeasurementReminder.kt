package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder

import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.Reminder
import java.time.LocalDate
import java.time.LocalTime

class FakeMeasurementReminder(
    val name: String,
    val unit: String,
    override var date: String,
    override var time: String,
    val value: Float=0F,
    override var done: Int=55,
    override var ID: String="-1"
):FakeReminder {
    override fun createRealReminder(): Reminder {
        return MeasurementReminder(
            name, unit, LocalDate.parse(date), LocalTime.parse(time), value,
            Controller.getIntToReminderStatus(done), ID
        )
    }
}