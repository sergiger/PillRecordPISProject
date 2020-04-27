package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy.FakeMedicineTherapy
import com.google.gson.Gson
import java.time.LocalDate

open class MedicineTherapy(
    override var frequency: Frequency,
    override var startDate: LocalDate,
    override var endDate: LocalDate,
    override var notes: String="",
    override var id: String="",
    var dose:Int,
    var units:String,
    var medicine:String

) : Therapy {
    override fun createFakeTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy.FakeTherapy {
        return FakeMedicineTherapy(Gson().toJson(frequency),startDate.toString(),endDate.toString(),notes,id,dose,units,medicine)
    }
}