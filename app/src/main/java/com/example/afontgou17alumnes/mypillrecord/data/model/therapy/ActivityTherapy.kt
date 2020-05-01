package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeActivityTherapy
import com.google.gson.Gson
import java.time.LocalDate

class ActivityTherapy(
    override var frequency: Frequency,
    override var notes: String="",
    override var id: String="-1",
    var activityType:String,
    var duration:Int,
    override var hours: ArrayList<String>
) : Therapy {
    override fun createFakeTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy {
        return FakeActivityTherapy(Gson().toJson(frequency.createFakeFrequency()),notes,id,activityType,duration,hours)
    }
}
