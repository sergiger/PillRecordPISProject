package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MeasurementTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import com.google.gson.Gson
import java.time.LocalDate

class FakeMeasurementTherapy(
    override var frequency: String,
    override var notes: String,
    override var id: String,
    var measurementType:String,
    override var hours: ArrayList<String>
) :FakeTherapy {
    override fun createRealTherapy(): Therapy {
        var frequecy: FakeFrequency = Gson().fromJson(frequency, FakeFrequency::class.java)
        return MeasurementTherapy(
            frequecy.createRealFrequency(),
            notes,
            id,
            measurementType,
            hours
        )
    }
}