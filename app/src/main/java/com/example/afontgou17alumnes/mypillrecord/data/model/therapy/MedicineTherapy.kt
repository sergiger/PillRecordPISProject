package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ReminderStatus
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeMedicineTherapy
import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalTime

open class MedicineTherapy(
    override var frequency: Frequency,
    override var notes: String="",
    override var id: String="-1",
    var dose:Int,
    var units:String,
    var medicine:String,
    override var hours: ArrayList<String>

) : Therapy {
    override fun createFakeTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy {
        return FakeMedicineTherapy(Gson().toJson(frequency.createFakeFrequency()),notes,id,dose,units,medicine,hours)
    }

    override fun createReminders() {
        var date=LocalDate.now()
        var endDate=LocalDate.now()
        if(this.frequency.type!=4){
            var dataString:String=frequency.getRealDateString(frequency.startDate)
            date = LocalDate.parse(dataString) //date està a la data inicial, i anirem modificant per anar generant els diferents reminders

            dataString=frequency.getRealDateString(frequency.endDate)
            endDate = LocalDate.parse(dataString)
        }
        if(this.frequency.type==1){
            //Dayly, x times per day
            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                if(date.isAfter(LocalDate.now())||date.isEqual(LocalDate.now()))
                    for (hour in this.hours)
                        createOneReminder(date,LocalTime.parse(hour))
                date=date.plusDays(1)
            }
        }
        if(this.frequency.type==2) {
            //Every x days
            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                if(date.isAfter(LocalDate.now())||date.isEqual(LocalDate.now()))
                    for (hour in this.hours)
                        createOneReminder(date,LocalTime.parse(hour))
                date=date.plusDays(this.frequency.eachtimedose.toLong())
            }
        }
        if(this.frequency.type==3){
            //Specific Week days
            var diesSetmana=this.frequency.getDiferenceBetweenDays()
            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                if(date.isAfter(LocalDate.now())||date.isEqual(LocalDate.now()))
                    if(diesSetmana[date.dayOfWeek.value-1]==1)
                        for (hour in this.hours)
                            createOneReminder(date,LocalTime.parse(hour))
                date=date.plusDays(1)
            }
        }
        if(this.frequency.type==4){
            //Punctual days
            for(dates in this.frequency.listofpuntualdays)
                for(hour in this.hours)
                    createOneReminder(LocalDate.parse(frequency.getRealDateString(dates)),LocalTime.parse(hour))
        }
    }

    override fun deleteReminders() {
        Controller.user.deleteAllRemindersById(this.id)
    }

    override fun toStringPDF(): String {
        return  "          "+"Medicine:  "+this.medicine+"\n               "+
                "Dose:  "+this.dose.toString()+"min"+"\n               "+
                "Units:"+this.units.toString()+"\n               "+
                "Notes: "+this.notes+
                "\n               "+
                this.frequency.toStringPDF()
    }

    override fun getName(): String {
        return this.medicine
    }

    fun createOneReminder(date:LocalDate,time:LocalTime){
        Controller.addReminder(
            MedicineReminder(
                this.medicine, this.dose,this.units, date, time,
                ReminderStatus.TO_DO, this.id
            )
        )
    }
}