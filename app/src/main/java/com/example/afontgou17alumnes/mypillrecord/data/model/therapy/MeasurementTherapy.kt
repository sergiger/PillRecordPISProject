package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

import android.util.Log
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ReminderStatus
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeMeasurementTherapy
import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalTime

class MeasurementTherapy(
    override var frequency: Frequency,
    override var notes: String="",
    override var id: String="-1",
    var measurementType:String,
    override var hours: ArrayList<String>
) : Therapy {
    override fun createFakeTherapy(): com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy {
        return FakeMeasurementTherapy(Gson().toJson(frequency.createFakeFrequency()),notes,id,measurementType,hours)
    }

    override fun createReminders() {
        val measurement_types = arrayOf("Weight", "Heart Rate","Arterial Pressure","Temperature","Glucose (before eating)","Glucose (after eating)")
        val unit_types=arrayOf("Kg","bpm","mmHg","Cº","mg/dl","mg/dl")
        var units:String="kg"
        var i=0
        Log.d("Type de measurement",this.measurementType)
        for (type in measurement_types){
            if(type.equals(this.measurementType)){
                units=unit_types[i]
                Log.d("he entrar","bingo")
            }
            i++
        }

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
                        createOneReminder(units,date,LocalTime.parse(hour))
                date=date.plusDays(1)
            }
        }
        if(this.frequency.type==2) {
            //Every x days
            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                if(date.isAfter(LocalDate.now())||date.isEqual(LocalDate.now()))
                    for (hour in this.hours)
                        createOneReminder(units,date,LocalTime.parse(hour))
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
                            createOneReminder(units,date,LocalTime.parse(hour))
                date=date.plusDays(1)
            }
        }
        if(this.frequency.type==4){
            //Punctual days
            for(dates in this.frequency.listofpuntualdays)
                for(hour in this.hours)
                    createOneReminder(units,LocalDate.parse(frequency.getRealDateString(dates)),LocalTime.parse(hour))
        }
    }

    override fun deleteReminders() {
        Controller.user.deleteAllRemindersById(this.id)
    }
    override fun toStringPDF(): String {
        return  "          "+"Measurement:"+this.measurementType+"\n               "+
                "Notes: "+this.notes+
                "\n               "+ this.frequency.toStringPDF()

    }

    override fun getName(): String {
        return this.measurementType
    }

    fun createOneReminder(units:String,date:LocalDate,time:LocalTime){
        Controller.addReminder(
            MeasurementReminder(
                this.measurementType, units, date, time,0F,
                ReminderStatus.TO_DO, this.id
            )
        )
    }
}