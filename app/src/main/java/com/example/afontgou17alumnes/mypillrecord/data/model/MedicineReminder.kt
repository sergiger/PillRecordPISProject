package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate
import java.time.LocalTime

open class MedicineReminder(
    //Potser alguns paràmetres es podrien agrupar a una classe Medicine
    val name: String,
    val dose: Int,
    val doseUnit: String,
    val date: LocalDate,
    val time: LocalTime
) : Reminder {
    /*constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readSerializable() as LocalDate,
        parcel.readSerializable() as LocalTime
    ) {

    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            //Això és crucial per poder utilitzar el constructor, ja que s'ha de poder llegir i escriure amb el mateix format
            dest.writeString(this.name)
            dest.writeInt(this.dose)
            dest.writeString(this.doseUnit)
            dest.writeSerializable(this.date) as LocalDate
            dest.writeSerializable(this.time) as LocalTime
        }
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<MedicineReminder> {
        override fun createFromParcel(parcel: Parcel): MedicineReminder {
            return MedicineReminder(parcel)
        }

        override fun newArray(size: Int): Array<MedicineReminder?> {
            return arrayOfNulls(size)
        }
    }*/


}