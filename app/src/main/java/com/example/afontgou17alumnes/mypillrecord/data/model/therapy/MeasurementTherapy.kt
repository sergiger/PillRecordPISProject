package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeMeasurementTherapy
import com.google.gson.Gson
import java.time.LocalDate

class MeasurementTherapy(
    override var frequency: Frequency,
    override var notes: String="",
    override var id: String="-1",
    var measurementType:String,
    override var hours: ArrayList<String>
) : Therapy {
    override fun createFakeTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy {
        return FakeMeasurementTherapy(Gson().toJson(frequency),notes,id,measurementType,hours)
    }
}