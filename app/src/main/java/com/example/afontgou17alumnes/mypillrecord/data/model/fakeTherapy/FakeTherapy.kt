package com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.model.Therapy
import com.google.gson.Gson
import java.time.LocalDate

interface FakeTherapy
    {
    var frequency: String
    var startDate: String
    var endDate: String
    var notes:String
    var id:String
    fun createRealTherapy():Therapy


}
