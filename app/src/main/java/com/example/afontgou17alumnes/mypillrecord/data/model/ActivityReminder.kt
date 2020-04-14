package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate
import java.time.LocalTime

class ActivityReminder(
    val name: String,
    val duration: Int,
    val date: LocalDate,
    val time: LocalTime,
    val done: Boolean=false
) : Reminder {
    override fun toString(): String {
        return "ActivityReminder(name='$name', duration=$duration, date=$date, time=$time, done=$done)"
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