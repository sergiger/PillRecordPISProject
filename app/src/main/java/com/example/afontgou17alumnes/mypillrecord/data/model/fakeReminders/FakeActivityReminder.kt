package com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders

import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.ReminderStatus
import java.time.LocalDate
import java.time.LocalTime

class FakeActivityReminder(
    val name: String,
    val duration: Int,
    override var date: String,
    override var time: String,
    override var done: Int=55
):FakeReminder {

    override fun createRealReminder(): com.example.afontgou17alumnes.mypillrecord.data.model.Reminder {
        return ActivityReminder(name, duration, LocalDate.parse(date), LocalTime.parse(time),
            Controller.getIntToReminderStatus(done))
    }
}