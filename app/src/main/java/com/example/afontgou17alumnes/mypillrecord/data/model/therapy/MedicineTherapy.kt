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
        if(this.frequency.type==1){
            //Dayly, x times per day
            var dataString:String=frequency.getRealDateString(frequency.startDate)
            var date = LocalDate.parse(dataString) //date està a la data inicial, i anirem modificant per anar generant els diferents reminders

            dataString=frequency.getRealDateString(frequency.endDate)
            var endDate = LocalDate.parse(dataString)

            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                for (hour in this.hours)
                    Controller.addReminder(
                        MedicineReminder(
                            this.medicine, this.dose,this.units, date, LocalTime.parse(hour),
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
                        MedicineReminder(
                            this.medicine, this.dose,this.units, date, LocalTime.parse(hour),
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
                            MedicineReminder(
                                this.medicine, this.dose,this.units, date, LocalTime.parse(hour),
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
                        MedicineReminder(
                            this.medicine, this.dose,this.units, LocalDate.parse(frequency.getRealDateString(dates)), LocalTime.parse(hour),
                            ReminderStatus.TO_DO, this.id
                        )
                    )
        }
    }

    override fun deleteReminders() {
        Controller.user.deleteAllRemindersById(this.id)
    }

    override fun toStringPDF(): String {
        return  "          "+"Medicine:  "+this.medicine+"\n               "+
                "Dose:  "+this.dose.toString()+"min"+"\n               "+
                "Units:"+this.units.toString()+"\n               "+
                "From: "+this.frequency.startDate+"\n" +
                "               "+
                "To: "+this.frequency.endDate+"\n" +
                "               "+
                "Notes: "+this.notes+"\n"
    }
}