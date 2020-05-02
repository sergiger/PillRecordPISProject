package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeFrequency

class Frequency{
    var type : Int = 0
    var startDate: String = ""
    var endDate: String = ""
    var listofpuntualdays = arrayOf<String>()
    var specificweekdays =arrayOf<String>()
    var eachtimedose : Int = 0

    constructor()
    constructor(startDate: String, endDate: String ){//haurem de tenir varis constructors, un per a cada tipus de frequencia
        //Aquest és el constructor vuit i encara podriem fer un altre constructor amb tota la informació
        this.type =1
        this.startDate =startDate
        this.endDate=endDate
    }
    constructor(startDate: String, endDate: String, eachtimedose:Int ){
        this.type =2
        this.startDate =startDate
        this.endDate=endDate
        this.eachtimedose =eachtimedose
    }
    constructor(startDate: String, endDate: String,specificweekdays: Array<String>){
        this.type =3
        this.startDate =startDate
        this.endDate=endDate
        this.specificweekdays = specificweekdays
    }
    constructor(listofpuntualdays: Array<String>){
        this.type =4
        this.listofpuntualdays = listofpuntualdays
    }
    constructor(type:Int, startDate: String, endDate: String, listofpuntualdays: Array<String>, specificweekdays: Array<String>, eachtimedose: Int){
        this.type=type
        this.startDate=startDate
        this.endDate=endDate
        this.eachtimedose=eachtimedose
        this.listofpuntualdays=listofpuntualdays
        this.specificweekdays=specificweekdays
    }



    override fun toString(): String {
        when(type){
            1-> return "Type "+ type + " startDate "+startDate + " endDate "+ endDate
            2-> return "Type "+ type + " startDate "+startDate + " endDate "+ endDate + " eachtimedose "+ eachtimedose
            3-> return "Type "+ type + " startDate "+startDate + " endDate "+ endDate + " specificweekdays "+ specificweekdays.contentToString()
            4-> return "Type "+ type + " listofpuntualdays "+ listofpuntualdays.contentToString()
        }
        return "error del ToString fun"
    }

    fun createFakeFrequency():FakeFrequency{
        return FakeFrequency(type,startDate,endDate,listofpuntualdays,specificweekdays,eachtimedose)
    }

}