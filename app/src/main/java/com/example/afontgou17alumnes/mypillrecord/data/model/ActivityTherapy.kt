package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy.FakeActivityTherapy
import com.google.gson.Gson
import java.time.LocalDate

class ActivityTherapy(
    override var frequency: Frequency,
    override var startDate: LocalDate,
    override var endDate: LocalDate,
    override var notes: String="",
    override var id: String="",
    var activityType:String,
    var duration:Int
) :Therapy{
    override fun createFakeTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy.FakeTherapy {
        return FakeActivityTherapy(Gson().toJson(frequency),startDate.toString(),endDate.toString(),notes,id,activityType,duration)
    }
}
