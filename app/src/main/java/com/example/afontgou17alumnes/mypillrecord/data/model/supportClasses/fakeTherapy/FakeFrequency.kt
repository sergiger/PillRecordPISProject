package com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy

import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Frequency

class FakeFrequency(
    var type:Int,
    var startDate: String,
    var endDate: String,
    var listofpuntualdays: Array<String>,
    var specificweekdays: Array<String>,
    var eachtimedose: Int) {
    fun createRealFrequency():Frequency{
        var retorn=Frequency()
        retorn.type=type
        retorn.eachtimedose=eachtimedose
        retorn.endDate=endDate
        retorn.listofpuntualdays=listofpuntualdays
        retorn.startDate=startDate
        retorn.specificweekdays=specificweekdays
       //return Frequency(type,startDate,endDate,listofpuntualdays,specificweekdays,eachtimedose)
        return retorn
    }
}