package com.example.afontgou17alumnes.mypillrecord.data.model.reminder

import android.util.Log
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder.FakeActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder.FakeReminder
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

class ActivityReminder(
    val name: String,
    var duration: Int,
    override var date: LocalDate= LocalDate.now(),
    override var time: LocalTime= LocalTime.of(LocalTime.now().hour,LocalTime.now().minute+5),
    override var status: ReminderStatus = ReminderStatus.TO_DO,
    override var ID:String="-1"
) : Reminder {


    override fun toString(): String {
        return "ActivityReminder(name='$name', duration=$duration, date=$date, time=$time, done=$status)"
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
        return FakeActivityReminder(name,duration,date.toString(),time.toString(),Controller.getReminderStatusToInt(status),ID)
    }

    override fun toStringPDF(): String {
        var retorn=""
        retorn="\n     Activity:  "+this.name+
                "\n" +
                "        Duration:  "+this.duration+"min"+
                "\n" +
                "        Time: "+this.time.toString()+
                "\n" +
                "        Status:  "+this.status.name+"\n"
        return retorn
    }

    override fun toStringPDF_calendar(): String{
        var retorn=""
        retorn="\n     Activity:  "+this.name+
                "\n" +
                "        Date:  "+this.date.toString()+
                "\n" +
                "        Duration:  "+this.duration+"min"+
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
        if(this.date.equals(LocalDate.now().plusDays(1))) {
            Log.d("time", time.toString())
            Log.d("date", date.toString())
            Log.d("result", result.toString())
            Log.d("real,", Calendar.getInstance().timeInMillis.toString())
            Log.d("reminder", this.toStringPDF())
            Log.d("diference in minutes",((result-Calendar.getInstance().timeInMillis)/(60.0*1000*60)).toString())
        }
        return result
    }

    override fun getReminderStatus(): ReminderStatus {
        return status
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ActivityReminder

        if (name != other.name) return false
        if (duration != other.duration) return false
        if (date != other.date) return false
        if (time != other.time) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + duration
        result = 31 * result + date.hashCode()
        result = 31 * result + time.hashCode()
        return result
    }


}