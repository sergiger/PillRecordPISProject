package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy
import java.time.LocalDate

interface Therapy{
    var frequency: Frequency
    var notes:String
    var id:String
    var hours : ArrayList<String>

    fun createFakeTherapy():FakeTherapy

    fun createReminders()

    fun deleteReminders()
    fun toStringPDF(): String
}

