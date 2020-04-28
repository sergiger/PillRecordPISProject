package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy
import java.time.LocalDate

interface Therapy{
    var frequency: Frequency
    var startDate:LocalDate
    var endDate:LocalDate
    var notes:String
    var id:Int

    fun createFakeTherapy():FakeTherapy
}

