package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy.FakeTherapy
import com.google.gson.Gson
import java.time.LocalDate

interface Therapy{
    var frequency:Frequency
    var startDate:LocalDate
    var endDate:LocalDate
    var notes:String
    var id:String

    fun createFakeTherapy():FakeTherapy
}

