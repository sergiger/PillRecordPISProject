package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcelable
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeReminder
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

interface Reminder : Serializable{

    var date: LocalDate
    var time : LocalTime
    var status : ReminderStatus

    fun getReminderName(): String
    fun getHour(): LocalTime
    fun getMilisFromNow(): Long //Retorna els milisegons que cal posar per a la notificació
    fun getReminderStatus(): ReminderStatus
    fun getReminderDate(): LocalDate
    fun createFakeReminder():FakeReminder
} /*:Parcelable*/