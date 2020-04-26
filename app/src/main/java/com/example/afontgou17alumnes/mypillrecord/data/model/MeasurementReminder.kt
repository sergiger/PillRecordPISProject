package com.example.afontgou17alumnes.mypillrecord.data.model

import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeMeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeReminder
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

class MeasurementReminder(
    val name: String,
    val unit: String,
    override var date: LocalDate,
    override var time: LocalTime,
    var value: Float=0F,
    override var status:ReminderStatus=ReminderStatus.TO_DO
): Reminder {
    override fun toString(): String {
        return "MeasurementReminder(name='$name', unit='$unit', value=$value, date=$date, time=$time)"
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
        return FakeMeasurementReminder(name,unit,date.toString(),time.toString(),value,status)
    }

    override fun getMilisFromNow(): Long {
        var result : Long = Calendar.getInstance().timeInMillis
        var date:Long
        var time:Long
        time=this.time.hour.toLong()*60*60*1000+this.time.minute.toLong()*60*1000-(LocalTime.now().hour*60*60*1000+LocalTime.now().minute*60*1000)
        if(this.date.isBefore(LocalDate.now())){
            date=-2*24*60*60*1000
        }
        else {
            date = ChronoUnit.DAYS.between(this.date, LocalDate.now()) * 24 * 60 * 60 * 1000
        }
        result+=time+date
        return result
    }

    override fun getReminderStatus(): ReminderStatus {
        return status
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MeasurementReminder

        if (name != other.name) return false
        if (unit != other.unit) return false
        if (value != other.value) return false
        if (date != other.date) return false
        if (time != other.time) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + unit.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + time.hashCode()
        return result
    }

}