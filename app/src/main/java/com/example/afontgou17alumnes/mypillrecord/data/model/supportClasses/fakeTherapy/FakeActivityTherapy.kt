package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.ActivityTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import com.google.gson.Gson
import java.time.LocalDate

class FakeActivityTherapy(
    override var frequency: String,
    override var startDate: String,
    override var endDate: String,
    override var notes: String,
    override var id: Int,
    var activityType:String,
    var duration:Int

) :FakeTherapy {
    override fun createRealTherapy(): Therapy {
        var frequecy: Frequency = Gson().fromJson(frequency, Frequency::class.java)
        return ActivityTherapy(
            frequecy,
            LocalDate.parse(startDate),
            LocalDate.parse(endDate),
            notes,
            id,
            activityType,
            duration
        )

    }
}