package com.example.afontgou17alumnes.mypillrecord.data.model.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.ActivityTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.model.MedicineTherapy
import com.google.gson.Gson
import java.time.LocalDate

class FakeActivityTherapy(
    override var frequency: String,
    override var startDate: String,
    override var endDate: String,
    override var notes: String,
    override var id: String,
    var activityType:String,
    var duration:Int

) :FakeTherapy {
    override fun createRealTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.Therapy {
        var frequecy: Frequency = Gson().fromJson(frequency, Frequency::class.java)
        return ActivityTherapy(frequecy,LocalDate.parse(startDate), LocalDate.parse(endDate),notes,id,activityType,duration)

    }
}