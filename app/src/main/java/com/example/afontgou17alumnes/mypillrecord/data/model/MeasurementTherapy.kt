package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class MeasurementTherapy:Therapy {
    var measurementType:String
        get()=field
        set(value) {field=value}

    constructor(startDate: LocalDate, endDate: LocalDate, frequency: Frequency, notes:String,type:String)
            : super(startDate,endDate,frequency,notes){
        this.measurementType=type
    }
    constructor(source: Parcel) :super(source){
        this.measurementType=source.readString()
    }
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            //Això és crucial per poder utilitzar el constructor, ja que s'ha de poder llegir i escriure amb el mateix format
            dest.writeSerializable(this.startDate) as LocalDate
            dest.writeSerializable(this.endDate) as LocalDate
            dest.writeParcelable(this.frequency)
            dest.writeString(this.notes)
            dest.writeString(this.measurementType)
        }
    }
    override fun toString(): String {
        return "MeasurementTherapy(frequency=$frequency, startDate=$startDate, endDate=$endDate, notes='$notes', measurementType=$measurementType)"
    }

    companion object CREATOR : Parcelable.Creator<MeasurementTherapy> {
        override fun createFromParcel(parcel: Parcel): MeasurementTherapy {
            return MeasurementTherapy(parcel)
        }

        override fun newArray(size: Int): Array<MeasurementTherapy?> {
            return arrayOfNulls(size)
        }
    }

}