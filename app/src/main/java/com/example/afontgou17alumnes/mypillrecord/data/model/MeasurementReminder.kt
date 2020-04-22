package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate
import java.time.LocalTime

class MeasurementReminder(
    val name: String,
    val unit: String,
    override var date: LocalDate,
    override var time: LocalTime,
    val value: Float=0F,
    override var done:Boolean=false
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