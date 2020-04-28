package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy

interface FakeTherapy
    {
    var frequency: String
    var startDate: String
    var endDate: String
    var notes:String
    var id:Int
    fun createRealTherapy(): Therapy


}
