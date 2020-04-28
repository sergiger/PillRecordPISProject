package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder

import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.Reminder
import java.time.LocalDate
import java.time.LocalTime

class FakeActivityReminder(
    val name: String,
    val duration: Int,
    override var date: String,
    override var time: String,
    override var done: Int=55,
    override var ID: Int=-1
):FakeReminder {

    override fun createRealReminder(): Reminder {
        return ActivityReminder(
            name, duration, LocalDate.parse(date), LocalTime.parse(time),
            Controller.getIntToReminderStatus(done), ID
        )
    }
}