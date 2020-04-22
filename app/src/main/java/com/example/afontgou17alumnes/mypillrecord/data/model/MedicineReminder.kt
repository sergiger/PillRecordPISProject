package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

open class MedicineReminder(
    //Potser alguns paràmetres es podrien agrupar a una classe Medicine
    val name: String,
    val dose: Int,
    val doseUnit: String,
    override var date: LocalDate,
    override var time: LocalTime,
    override var done:Boolean=false
) : Reminder {
    override fun toString(): String {//Es només per provar que les coses funcionen
        return "MedicineReminder(name='$name', dose=$dose, doseUnit='$doseUnit', date=$date, time=$time, taken=$done)"
    }

    override fun getReminderName(): String {
        return name
    }

    override fun getHour(): LocalTime {
        return time
    }

    override fun getReminderDate(): LocalDate {
        return date
    }

    override fun getMilisFromNow(): Long {
        var result : Long = Calendar.getInstance().timeInMillis
        var date:Long
        var time:Long
        time=this.time.hour.toLong()*60*60*1000+this.time.minute.toLong()*60*1000-(LocalTime.now().hour*60*60*1000+LocalTime.now().minute*60*1000)
        date= ChronoUnit.DAYS.between(this.date,LocalDate.now())*24*60*60*1000
        result+=time+date
        return result
    }

    override fun isDone(): Boolean {
        var retorn=false
        if(taken)
            retorn=true
        return retorn
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MedicineReminder

        if (name != other.name) return false
        if (dose != other.dose) return false
        if (doseUnit != other.doseUnit) return false
        if (date != other.date) return false
        if (time != other.time) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + dose
        result = 31 * result + doseUnit.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + time.hashCode()
        return result
    }

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