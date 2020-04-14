package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcel
import android.os.Parcelable
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

    constructor(source: Parcel) :super(source){
        this.dose=source.readInt()
        this.units=source.readString()
        this.medicine=source.readString()
    }

    override fun toString(): String {
        return "MedicineTherapy(frequency=$frequency, startDate=$startDate, endDate=$endDate, notes='$notes', dose=$dose, units=$units, medicine=$medicine)"
    }
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            //Això és crucial per poder utilitzar el constructor, ja que s'ha de poder llegir i escriure amb el mateix format
            dest.writeSerializable(this.startDate) as LocalDate
            dest.writeSerializable(this.endDate) as LocalDate
            dest.writeParcelable(this.frequency)
            dest.writeString(this.notes)
            dest.writeInt(this.dose)
            dest.writeString(this.units)
            dest.writeString(this.medicine)
        }
    }

    companion object CREATOR : Parcelable.Creator<MedicineTherapy> {
        override fun createFromParcel(parcel: Parcel): MedicineTherapy {
            return MedicineTherapy(parcel)
        }

        override fun newArray(size: Int): Array<MedicineTherapy?> {
            return arrayOfNulls(size)
        }
    }

}