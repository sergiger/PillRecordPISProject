package com.example.afontgou17alumnes.mypillrecord.data.model.reminder

import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder.FakeMedicationReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder.FakeReminder
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

open class MedicineReminder(
    //Potser alguns paràmetres es podrien agrupar a una classe Medicine
    var name: String,
    var dose: Int,
    var doseUnit: String,
    override var date: LocalDate,
    override var time: LocalTime,
    override var status: ReminderStatus = ReminderStatus.TO_DO,
    override var ID:String="-1"
) : Reminder {
    override fun toString(): String {//Es només per provar que les coses funcionen
        return "MedicineReminder(name='$name', dose=$dose, doseUnit='$doseUnit', date=$date, time=$time, taken=$status)"
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

    override fun createFakeReminder(): FakeReminder {
        return FakeMedicationReminder(name,dose,doseUnit,date.toString(),time.toString(),
            Controller.getReminderStatusToInt(status),ID)
    }

    override fun toStringPDF(): String {
        var retorn=""
        retorn="\n     Medicine:  "+this.name+
                "\n" +
                "        Dose:  "+this.dose+
                "\n" +
                "        Units:  "+this.doseUnit+
                "\n" +
                "        Time: "+this.time.toString()+
                "\n" +
                "        Status:  "+this.status.name+"\n"
        return retorn
    }
    override fun toStringPDF_calendar(): String {
        var retorn=""
        retorn="\n     Medicine:  "+this.name+
                "\n" +
                "        Date:  "+this.date.toString()+
                "\n" +
                "        Dose:  "+this.dose+
                "\n" +
                "        Units:  "+this.doseUnit+
                "\n" +
                "        Time: "+this.time.toString()+
                "\n" +
                "        Status:  "+this.status.name+"\n"
        return retorn
    }

    override fun getMilisFromNow(): Long {
        var result : Long = Calendar.getInstance().timeInMillis
        var date:Long
        var time:Long
        time=this.time.hour.toLong()*60*60*1000+this.time.minute.toLong()*60*1000-(LocalTime.now().hour*60*60*1000+LocalTime.now().minute*60*1000)
        if(this.date.isBefore(LocalDate.now())){
            date=-2*24*60*60*1000//Aquest número és simplement perque el resultat sigui més petit que 0
        }
        else {
            date = ChronoUnit.DAYS.between(LocalDate.now(),this.date) * 24 * 60 * 60 * 1000
        }
        result+=time+date
        return result
    }

    override fun getReminderStatus(): ReminderStatus {
        return status
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