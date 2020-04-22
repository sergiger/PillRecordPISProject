package com.example.afontgou17alumnes.mypillrecord.data.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

interface Reminder : Serializable{
    var date: LocalDate
    var time : LocalTime
    var done : Boolean

    fun getReminderName(): String
    fun getHour(): LocalTime
    fun getMilisFromNow(): Long //Retorna els milisegons que cal posar per a la notificació
    fun isDone():Boolean
    fun getReminderDate(): LocalDate
} /*:Parcelable*/