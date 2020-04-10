package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class ActivityTherapy:Therapy {
    var activityType:String
        get()=field
        set(value) {field=value}
    var duration:Int
        get()=field
        set(value){field=value}

    constructor(startDate: LocalDate, endDate: LocalDate, frequency: Frequency, notes:String, activityType:String,duration: Int)
            : super(startDate,endDate,frequency,notes){
        this.activityType=activityType
        this.duration=duration
    }
    constructor(source:Parcel) :super(source){
        this.activityType=source.readString()
        this.duration=source.readInt()
    }
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            //Això és crucial per poder utilitzar el constructor, ja que s'ha de poder llegir i escriure amb el mateix format
            dest.writeSerializable(this.startDate) as LocalDate
            dest.writeSerializable(this.endDate) as LocalDate
            dest.writeParcelable(this.frequency)
            dest.writeString(this.notes)
            dest.writeString(this.activityType)
            dest.writeInt(this.duration)
        }
    }

    companion object CREATOR : Parcelable.Creator<ActivityTherapy> {
        override fun createFromParcel(parcel: Parcel): ActivityTherapy {
            return ActivityTherapy(parcel)
        }

        override fun newArray(size: Int): Array<ActivityTherapy?> {
            return arrayOfNulls(size)
        }
    }
}