package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeActivityTherapy
import com.google.gson.Gson
import java.time.LocalDate

class ActivityTherapy(
    override var frequency: Frequency,
    override var startDate: LocalDate,
    override var endDate: LocalDate,
    override var notes: String="",
    override var id: Int=-1,
    var activityType:String,
    var duration:Int
) : Therapy {
    override fun createFakeTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy {
        return FakeActivityTherapy(Gson().toJson(frequency),startDate.toString(),endDate.toString(),notes,id,activityType,duration)
    }
}
