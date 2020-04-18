package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class Frequency :Parcelable {
    var type : Int = 0
        get() = field
        set(value) {
            field = value
        }
    var startDate: String = ""
        get() = field
        set(value) {
            field = value
        }
    var endDate: String = ""
        get() = field
        set(value) {
            field = value
        }
    var listofpuntualdays = arrayOf<String>()
        get() = field
        set(value) {
            field = value
        }
    var specificweekdays =arrayOf<String>()
        get() = field
        set(value) {
            field = value
        }
    var eachtimedose : Int = 0
        get() = field
        set(value) {
            field = value
        }

    constructor(parcel: Parcel){
        //aqui has de separar totes les parts del parel i posarles a cada variable this.
    }

    //Aqui posariem els paràmetres
    constructor(frequency: Frequency){
        //Aquest és el constructor copia
    }
    constructor(){

    }
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


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            //aqui es montaria el parcel i es posaria tot dintre del dest de la seguent manera
            //dest.writeSerializable(this.startDate) as LocalDate
        }
    }

    override fun describeContents(): Int {
        return 0
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

    companion object CREATOR : Parcelable.Creator<Frequency> {
        override fun createFromParcel(parcel: Parcel): Frequency {
            return Frequency(parcel)
        }

        override fun newArray(size: Int): Array<Frequency?> {
            return arrayOfNulls(size)
        }
    }
}