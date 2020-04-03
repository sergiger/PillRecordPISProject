package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate

class MeasurementTherapy:Therapy {
    var measurementType:String
        get()=field
        set(value) {field=value}

    constructor(startDate: LocalDate, endDate: LocalDate, frequency: Frequency, notes:String,type:String)
            : super(startDate,endDate,frequency,notes){
        this.measurementType=type
    }
}