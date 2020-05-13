package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy
import java.io.Serializable
import java.time.LocalDate

interface Therapy: Serializable{
    var frequency: Frequency
    var notes:String
    var id:String
    var hours : ArrayList<String>

    fun createFakeTherapy():FakeTherapy

    fun createReminders()

    fun deleteReminders()
    fun toStringPDF(): String
    fun getName(): String
}

