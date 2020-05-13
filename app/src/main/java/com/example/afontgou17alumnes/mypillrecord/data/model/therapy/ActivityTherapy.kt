package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import android.util.Log
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ReminderStatus
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeActivityTherapy
import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalTime

class ActivityTherapy(
    override var frequency: Frequency,
    override var notes: String="",
    override var id: String="-1",
    var activityType:String,
    var duration:Int,
    override var hours: ArrayList<String>
) : Therapy {
    override fun createFakeTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy {
        return FakeActivityTherapy(Gson().toJson(frequency.createFakeFrequency()),notes,id,activityType,duration,hours)
    }

    override fun createReminders() {
        if(this.frequency.type==1){
            //Dayly, x times per day
            var dataString:String=frequency.getRealDateString(frequency.startDate)
            var date = LocalDate.parse(dataString) //date està a la data inicial, i anirem modificant per anar generant els diferents reminders

            dataString=frequency.getRealDateString(frequency.endDate)
            var endDate = LocalDate.parse(dataString)

            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                for (hour in this.hours)
                    Controller.addReminder(ActivityReminder(
                        this.activityType, this.duration, date, LocalTime.parse(hour),
                        ReminderStatus.TO_DO, this.id
                    ))
                date=date.plusDays(1)
            }
        }
        if(this.frequency.type==2) {
            //Every x days
            var dataString:String=frequency.getRealDateString(frequency.startDate)
            var date = LocalDate.parse(dataString) //date està a la data inicial, i anirem modificant per anar generant els diferents reminders

            dataString=frequency.getRealDateString(frequency.endDate)
            var endDate = LocalDate.parse(dataString)

            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                for (hour in this.hours)
                    Controller.addReminder(ActivityReminder(
                        this.activityType, this.duration, date, LocalTime.parse(hour),
                        ReminderStatus.TO_DO, this.id
                    ))
                date=date.plusDays(this.frequency.eachtimedose.toLong())
            }
        }
        if(this.frequency.type==3){
            //Specific Week days
            var diesSetmana=this.frequency.getDiferenceBetweenDays()

            var dataString:String=frequency.getRealDateString(frequency.startDate)
            var date = LocalDate.parse(dataString) //date està a la data inicial, i anirem modificant per anar generant els diferents reminders

            dataString=frequency.getRealDateString(frequency.endDate)
            var endDate = LocalDate.parse(dataString)

            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                if(diesSetmana[date.dayOfWeek.value-1]==1)
                    for (hour in this.hours)
                        Controller.addReminder(ActivityReminder(
                            this.activityType, this.duration, date, LocalTime.parse(hour),
                            ReminderStatus.TO_DO, this.id
                        ))
                date=date.plusDays(1)
            }
        }
        if(this.frequency.type==4){
            //Punctual days
            for(dates in this.frequency.listofpuntualdays)
                for(hour in this.hours)
                    Controller.addReminder(ActivityReminder(
                        this.activityType, this.duration, LocalDate.parse(frequency.getRealDateString(dates)), LocalTime.parse(hour),
                        ReminderStatus.TO_DO, this.id
                    ))
        }
    }

    override fun deleteReminders() {
        Controller.user.deleteAllRemindersById(this.id)
    }

    override fun toStringPDF(): String {
        return "          "+"Activity:  "+this.activityType+"\n               "+
                "Duration:  "+this.duration.toString()+"min"+"\n" +
                "               "+
                "From: "+this.frequency.startDate+"\n" +
                "               "+
                "To: "+this.frequency.endDate+"\n" +
                "               "+
                "Notes: "+this.notes+"\n"+"\n"
    }

    override fun getName(): String {
        return this.activityType
    }
}
