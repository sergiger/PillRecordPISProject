package com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders

import com.example.afontgou17alumnes.mypillrecord.data.model.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.ReminderStatus
import java.time.LocalDate
import java.time.LocalTime

class FakeMeasurementReminder(
    val name: String,
    val unit: String,
    override var date: String,
    override var time: String,
    val value: Float=0F,
    override var done: ReminderStatus=ReminderStatus.TO_DO
):FakeReminder {
    override fun createRealReminder(): com.example.afontgou17alumnes.mypillrecord.data.model.Reminder {
        return MeasurementReminder(name,unit, LocalDate.parse(date), LocalTime.parse(time),value,done)
    }
}