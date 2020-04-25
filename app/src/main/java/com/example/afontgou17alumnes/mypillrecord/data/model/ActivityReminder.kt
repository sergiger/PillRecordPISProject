package com.example.afontgou17alumnes.mypillrecord.data.model

import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeReminder
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*



class ActivityReminder(
    val name: String,
    val duration: Int,
    override var date: LocalDate= LocalDate.now(),
    override var time: LocalTime= LocalTime.of(LocalTime.now().hour,LocalTime.now().minute+5),
    override var done: Boolean=false
) : Reminder {
    override fun toString(): String {
        return "ActivityReminder(name='$name', duration=$duration, date=$date, time=$time, done=$done)"
    }

    override fun getReminderName(): String {
        return name
    }

    override fun getHour(): LocalTime {
        return time
    }

    override fun getReminderDate(): LocalDate {
        return date
    }

    override fun createFakeReminder(): FakeReminder {
        return FakeActivityReminder(name,duration,date.toString(),time.toString(),done)
    }

    override fun getMilisFromNow(): Long {
        var result : Long = Calendar.getInstance().timeInMillis
        var date:Long
        var time:Long
        time=this.time.hour.toLong()*60*60*1000+this.time.minute.toLong()*60*1000-(LocalTime.now().hour*60*60*1000+LocalTime.now().minute*60*1000)
        date= ChronoUnit.DAYS.between(this.date,LocalDate.now())*24*60*60*1000
        result+=time+date
        return result
    }

    override fun isDone(): Boolean {
        return done
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ActivityReminder

        if (name != other.name) return false
        if (duration != other.duration) return false
        if (date != other.date) return false
        if (time != other.time) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + duration
        result = 31 * result + date.hashCode()
        result = 31 * result + time.hashCode()
        return result
    }

}