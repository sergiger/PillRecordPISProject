package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder

import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.Reminder

interface FakeReminder {
    var time:String
    var date:String
    var done:Int
    var ID:Int
    fun createRealReminder(): Reminder
}