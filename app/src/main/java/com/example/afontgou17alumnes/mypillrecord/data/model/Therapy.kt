package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate

abstract class Therapy {
    var frequency=Frequency()
    var startDate:LocalDate
        get()=field
        set(value) {field=value}
    var endDate:LocalDate
        get()=field
        set(value) {field=value}
    var notes:String
        get()=field
        set(value) {field=value}


    constructor(startDate:LocalDate,endDate:LocalDate, frequency: Frequency,notes:String){
        this.startDate=startDate
        this.endDate=endDate
        this.frequency=frequency
        this.notes=notes
    }



}