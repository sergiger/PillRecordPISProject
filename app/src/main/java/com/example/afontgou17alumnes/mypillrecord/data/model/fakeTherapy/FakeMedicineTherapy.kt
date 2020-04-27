package com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.model.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.MedicineTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.Therapy
import com.google.gson.Gson
import java.time.LocalDate

class FakeMedicineTherapy(
    override var frequency: String,
    override var startDate: String,
    override var endDate: String,
    override var notes: String,
    override var id: String,
    var dose:Int,
    var units:String,
    var medicine:String
) :FakeTherapy {
    override fun createRealTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.Therapy {
        var frequecy: Frequency = Gson().fromJson(frequency, Frequency::class.java)
        return MedicineTherapy(frequecy,LocalDate.parse(startDate), LocalDate.parse(endDate),notes,id,dose,units,medicine)
    }
}

