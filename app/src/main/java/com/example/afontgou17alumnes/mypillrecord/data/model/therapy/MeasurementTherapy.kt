package com.example.afontgou17alumnes.mypillrecord.data.model.therapy

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
        val measurement_types = arrayOf("Weight", "Heart rate","Arterial pressure","Temperature","Glucose level(before eating)","Glucose level(after eating)")
        val unit_types=arrayOf("Kg","bpm","mmHg","Cº","mg/dl","mg/dl")
        var units:String="kg"
        var i=0
        for (type in measurement_types){
            if(type==this.measurementType)
                units=unit_types[i]
            i++
        }

        if(this.frequency.type==1){
            //Dayly, x times per day
            var dataString:String=frequency.getRealDateString(frequency.startDate)
            var date = LocalDate.parse(dataString) //date està a la data inicial, i anirem modificant per anar generant els diferents reminders

            dataString=frequency.getRealDateString(frequency.endDate)
            var endDate = LocalDate.parse(dataString)

            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                for (hour in this.hours)
                    Controller.addReminder(
                        MeasurementReminder(
                            this.measurementType, units, date, LocalTime.parse(hour),0F,
                            ReminderStatus.TO_DO, this.id
                        )
                    )
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
                    Controller.addReminder(
                        MeasurementReminder(
                            this.measurementType, units, date, LocalTime.parse(hour),0F,
                            ReminderStatus.TO_DO, this.id
                        )
                    )
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
                        Controller.addReminder(
                            MeasurementReminder(
                                this.measurementType, units, date, LocalTime.parse(hour),0F,
                                ReminderStatus.TO_DO, this.id
                            )
                        )
                date=date.plusDays(1)
            }
        }
        if(this.frequency.type==4){
            //Punctual days
            for(dates in this.frequency.listofpuntualdays)
                for(hour in this.hours)
                    Controller.addReminder(
                        MeasurementReminder(
                            this.measurementType, units, LocalDate.parse(frequency.getRealDateString(dates)), LocalTime.parse(hour),0F,
                            ReminderStatus.TO_DO, this.id
                        )
                    )
        }
    }

    override fun deleteReminders() {
        Controller.user.deleteAllRemindersById(this.id)
    }
    override fun toStringPDF(): String {
        return  "          "+"Measurement:"+this.measurementType+"\n               "+

                "From: "+this.frequency.startDate+"\n" +
                "               "+
                "To: "+this.frequency.endDate+"\n" +
                "               "+
                "Notes: "+this.notes+"\n"

    }
}