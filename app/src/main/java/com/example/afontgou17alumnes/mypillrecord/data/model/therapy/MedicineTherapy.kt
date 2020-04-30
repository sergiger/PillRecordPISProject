package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeMedicineTherapy
import com.google.gson.Gson
import java.time.LocalDate

open class MedicineTherapy(
    override var frequency: Frequency,
    override var notes: String="",
    override var id: String="-1",
    var dose:Int,
    var units:String,
    var medicine:String,
    override var hours: ArrayList<String>

) : Therapy {
    override fun createFakeTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy {
        return FakeMedicineTherapy(Gson().toJson(frequency),notes,id,dose,units,medicine,hours)
    }
}