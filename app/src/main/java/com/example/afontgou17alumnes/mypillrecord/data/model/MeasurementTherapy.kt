package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy.FakeMeasurementTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy.FakeMedicineTherapy
import com.google.gson.Gson
import java.time.LocalDate

class MeasurementTherapy(
    override var frequency: Frequency,
    override var startDate: LocalDate,
    override var endDate: LocalDate,
    override var notes: String="",
    override var id: String="",
    var measurementType:String
) :Therapy {
    override fun createFakeTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy.FakeTherapy {
        return FakeMeasurementTherapy(Gson().toJson(frequency),startDate.toString(),endDate.toString(),notes,id,measurementType)
    }
}