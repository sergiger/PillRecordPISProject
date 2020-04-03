package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate

open class MedicineTherapy: Therapy {
    var dose:Int
        get()=field
        set(value) {field=value}
    var units:String
        get()=field
        set(value) {field=value}
    var medicine:String
        get()=field
        set(value) {field=value}

    constructor(startDate: LocalDate, endDate: LocalDate, frequency: Frequency,notes:String,dose:Int,units:String,medicine:String)
            : super(startDate,endDate,frequency,notes) {
        this.dose=dose
        this.units=units
        this.medicine=medicine
    }

}