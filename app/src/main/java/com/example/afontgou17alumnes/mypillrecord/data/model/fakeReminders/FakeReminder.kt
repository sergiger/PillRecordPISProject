package com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders

import com.example.afontgou17alumnes.mypillrecord.data.model.Reminder
import com.example.afontgou17alumnes.mypillrecord.data.model.ReminderStatus

interface FakeReminder {
    var time:String
    var date:String
    var done:ReminderStatus
    fun createRealReminder(): Reminder
}