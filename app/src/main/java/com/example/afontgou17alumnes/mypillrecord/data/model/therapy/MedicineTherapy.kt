package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeMedicineTherapy
import com.google.gson.Gson
import java.time.LocalDate

open class MedicineTherapy(
    override var frequency: Frequency,
    override var startDate: LocalDate,
    override var endDate: LocalDate,
    override var notes: String="",
    override var id: Int=-1,
    var dose:Int,
    var units:String,
    var medicine:String

) : Therapy {
    override fun createFakeTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy {
        return FakeMedicineTherapy(Gson().toJson(frequency),startDate.toString(),endDate.toString(),notes,id,dose,units,medicine)
    }
}