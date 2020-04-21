package com.example.afontgou17alumnes.mypillrecord.data.model

import java.io.Serializable
import java.time.LocalTime

interface Reminder : Serializable{
    fun getReminderName(): String
    fun getHour(): LocalTime
} /*:Parcelable*/