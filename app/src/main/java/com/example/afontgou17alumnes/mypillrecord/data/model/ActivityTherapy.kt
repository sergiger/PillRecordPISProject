package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate

class ActivityTherapy:Therapy {
    var activityType:String
        get()=field
        set(value) {field=value}

    constructor(startDate: LocalDate, endDate: LocalDate, frequency: Frequency, notes:String, activityType:String)
            : super(startDate,endDate,frequency,notes){
        this.activityType=activityType
    }
}