package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MedicineTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import com.google.gson.Gson
import java.time.LocalDate

class FakeMedicineTherapy(
    override var frequency: String,
    override var notes: String,
    override var id: String,
    var dose:Int,
    var units:String,
    var medicine:String,
    override var hours: ArrayList<String>
) :FakeTherapy {
    override fun createRealTherapy(): Therapy {
        var frequecy: Frequency = Gson().fromJson(frequency, Frequency::class.java)
        return MedicineTherapy(
            frequecy,
            notes,
            id,
            dose,
            units,
            medicine,
            hours
        )
    }
}

