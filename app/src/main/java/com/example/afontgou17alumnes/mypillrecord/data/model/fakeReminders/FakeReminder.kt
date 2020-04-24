package com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders

import com.example.afontgou17alumnes.mypillrecord.data.model.Reminder

interface FakeReminder {
    var time:String
    var date:String
    var done:Boolean
    fun createRealReminder(): Reminder
}