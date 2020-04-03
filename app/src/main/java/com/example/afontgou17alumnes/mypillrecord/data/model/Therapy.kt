package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate
import java.util.*

open class Therapy :Parcelable{
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


    constructor(startDate:LocalDate,endDate:LocalDate, frequency: Frequency ?= null,notes:String){
        this.startDate=startDate
        this.endDate=endDate
        if (frequency != null) {
            this.frequency=frequency
        }
        this.notes=notes
    }
    constructor(source: Parcel){
        this.startDate= source.readSerializable() as LocalDate
        this.endDate= source.readSerializable() as LocalDate
        this.frequency=source.readParcelable(Frequency::class.java.classLoader)
        this.notes=source.readString()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            //Això és crucial per poder utilitzar el constructor, ja que s'ha de poder llegir i escriure amb el mateix format
            dest.writeSerializable(this.startDate) as LocalDate
            dest.writeSerializable(this.endDate) as LocalDate
            dest.writeParcelable(this.frequency)
            dest.writeString(this.notes)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Therapy> {
        override fun createFromParcel(parcel: Parcel): Therapy {
            return Therapy(parcel)
        }

        override fun newArray(size: Int): Array<Therapy?> {
            return arrayOfNulls(size)
        }
    }





}

fun Parcel?.writeParcelable(frequency: Frequency) {
    //Aqui s'ha de fer el write parcel del frequency
}
