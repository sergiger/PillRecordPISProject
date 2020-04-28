package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MeasurementTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import com.google.gson.Gson
import java.time.LocalDate

class FakeMeasurementTherapy(
    override var frequency: String,
    override var startDate: String,
    override var endDate: String,
    override var notes: String,
    override var id: Int,
    var measurementType:String
) :FakeTherapy {
    override fun createRealTherapy(): Therapy {
        var frequecy: Frequency = Gson().fromJson(frequency, Frequency::class.java)
        return MeasurementTherapy(
            frequecy,
            LocalDate.parse(startDate),
            LocalDate.parse(endDate),
            notes,
            id,
            measurementType
        )
    }
}