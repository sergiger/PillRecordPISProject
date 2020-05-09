package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import android.util.Log
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeFrequency
import java.time.LocalDate

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

    fun getRealDateString(date:String):String{
        //Funció perquè la data està escrita en un format que no em permet fer el parcel a LocalDate, l'String que això em retorna és perfecte en aquest sentit
        var dataString:String=""
        var substrings=date.split("/")
        if(substrings[0].length==1){
            if(substrings[1].length==1)
                dataString=substrings[2]+"-"+"0"+substrings[1]+"-"+"0"+substrings[0]
            else
                dataString=substrings[2]+"-"+substrings[1]+"-"+"0"+substrings[0]
        }
        else {
            if (substrings[1].length == 1)
                dataString = substrings[2] + "-" + "0" + substrings[1] + "-" + substrings[0]
            else
                dataString = substrings[2] + "-" + substrings[1] + "-" + substrings[0]
        }
        return dataString
    }

    fun getDiferenceBetweenDays(): ArrayList<Int> {
        var llista1_0= arrayListOf<Int>(0,0,0,0,0,0,0)
        //llista1_0 tindrà 1 si vols fer algo al dia i i si hi ha 0 voldrà dir que no s'ha de fer res aquest dia
        for(dia in this.specificweekdays){
            if(dia=="Mon")
                llista1_0[0]=1
            if(dia=="Tue")
                llista1_0[1]=1
            if(dia=="Wed")
                llista1_0[2]=1
            if(dia=="Thu")
                llista1_0[3]=1
            if(dia=="Fri")
                llista1_0[4]=1
            if(dia=="Sat")
                llista1_0[5]=1
            if(dia=="Sun")
                llista1_0[6]=1

        }
        return llista1_0
    }

    fun isActive(): Boolean {
        var retorn=false
        var inici=LocalDate.parse(this.getRealDateString(this.startDate))
        var end=LocalDate.parse(this.getRealDateString(this.endDate))
        var now=LocalDate.now()
        if((now.isEqual(inici)||now.isAfter(inici))&&(now.isEqual(end)||now.isBefore(end)))
            retorn=true
        return retorn
    }


}