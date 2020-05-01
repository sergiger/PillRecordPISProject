package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.ActivityTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import com.google.gson.Gson
import java.time.LocalDate

class FakeActivityTherapy(
    override var frequency: String,
    override var notes: String,
    override var id: String,
    var activityType:String,
    var duration:Int,
    override var hours: ArrayList<String>

) :FakeTherapy {
    override fun createRealTherapy(): Therapy {
        var frequecy: FakeFrequency = Gson().fromJson(frequency,FakeFrequency::class.java)
        return ActivityTherapy(
            frequecy.createRealFrequency(),
            notes,
            id,
            activityType,
            duration,
            hours
        )

    }
}