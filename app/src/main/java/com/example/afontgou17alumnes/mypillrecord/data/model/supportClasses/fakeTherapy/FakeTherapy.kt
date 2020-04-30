package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy

interface FakeTherapy
    {
    var frequency: String
    var notes:String
    var id:String
    var hours : ArrayList<String>
    fun createRealTherapy(): Therapy


}
