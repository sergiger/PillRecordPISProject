package com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.model.MeasurementTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.MedicineTherapy
import com.google.gson.Gson
import java.time.LocalDate

class FakeMeasurementTherapy(
    override var frequency: String,
    override var startDate: String,
    override var endDate: String,
    override var notes: String,
    override var id: String,
    var measurementType:String
) :FakeTherapy {
    override fun createRealTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.Therapy {
        var frequecy: Frequency = Gson().fromJson(frequency, Frequency::class.java)
        return MeasurementTherapy(frequecy,LocalDate.parse(startDate), LocalDate.parse(endDate),notes,id,measurementType)
    }
}