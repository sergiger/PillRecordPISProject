package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import android.util.Log
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.*
import com.example.afontgou17alumnes.mypillrecord.data.model.statistics.Statistics
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.ActivityTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MeasurementTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MedicineTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder.FakeReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.team.Agenda

open class User{
    var id : String
    var email:String
    var username:String
    var pasword:String
    var gender:String
    var birthYear :Int
    var weight:Float
    var height:Float
    var therapies= ArrayList<Therapy>()
    var reminders=ArrayList<Reminder>()
    var agenda=
        Agenda()
    var statistics =
        Statistics()

    constructor(id:String,email:String, username:String, pasword:String, gender:String,
                birthYear:Int, weight:Float, height:Float){
        this.id=id
        this.birthYear=birthYear
        this.email=email
        this.username=username
        this.gender=gender
        this.weight=weight
        this.pasword=pasword
        this.height=height
    }


    fun checkPasword(pasword:String):Boolean{
        return this.pasword==pasword
    }

    fun addReminder(reminder: Reminder){
        /*var index=-1
        for(iterReminders in this.reminders){
            index+=1
            if(iterReminders.date.isAfter(reminder.date)||(iterReminders.date.equals(reminder.date)&&iterReminders.time.isAfter(reminder.time))){
                var sublist=this.reminders.subList(index,reminders.size)
                while(index<reminders.size)
                    this.reminders.removeAt(index)
                reminders.add(reminder)
                reminders.addAll(sublist)
                break
            }
        }
        if((index+1)==reminders.size)
            reminders.add(reminder)
        Controller.controllerSharePrefs.sharedUpLoad()*/
    }

    fun addTherapy(therapy: Therapy){
        this.therapies.add(therapy)
    }

    fun changePasword(oldPasword: String, newPasword: String, repetedNewPasword: String) {
        if(oldPasword==this.pasword){
            if(newPasword==repetedNewPasword){
                this.pasword=newPasword
            }else{
                TODO()//exceptions
            }
        }else{
            TODO()//exceptions
        }
    }

    fun getNextReminder(): Reminder {
        var retorn: Reminder =
            MeasurementReminder(
                "Weight",
                "kg",
                LocalDate.now().plusDays(30),
                LocalTime.now()
            )
        for(reminder in reminders){
            if(reminder.getMilisFromNow()>Calendar.getInstance().timeInMillis) {
                if(reminder.getMilisFromNow()<retorn.getMilisFromNow())
                    if (reminder.getReminderStatus() == ReminderStatus.TO_DO) {
                        retorn=reminder
                    }
            }
        }
        return retorn
    }
    fun areThereReminders():Boolean{
        var retorn=false
        for(reminder in reminders){
            if(reminder.getMilisFromNow()>Calendar.getInstance().timeInMillis) {
                if (reminder.getReminderStatus() == ReminderStatus.TO_DO) {
                    retorn = true
                    break
                }
            }
        }
        return retorn
    }//Aquesta funció retorna true si hi ha algun reminder en el futur que no estigui fet
        //false si no n'hi ha cap per recordar

    fun getFakeActivityReminders(): Array<FakeReminder?> {
        var acumulador=ArrayList<FakeReminder>()
        for(reminder in this.reminders){
            if(reminder is ActivityReminder) {
                acumulador.add(reminder.createFakeReminder())
            }
        }
        val retorn=arrayOfNulls<FakeReminder>(acumulador.size)
        acumulador.toArray(retorn)
        return retorn
    }
    fun getFakeMeasurementReminders(): Array<FakeReminder?> {
        var acumulador=ArrayList<FakeReminder>()
        for(reminder in this.reminders){
            if(reminder is MeasurementReminder) {
                acumulador.add(reminder.createFakeReminder())
            }
        }
        val retorn=arrayOfNulls<FakeReminder>(acumulador.size)
        acumulador.toArray(retorn)
        return retorn
    }
    fun getFakeMedicationReminders(): Array<FakeReminder?> {
        var acumulador=ArrayList<FakeReminder>()
        for(reminder in this.reminders){
            if(reminder is MedicineReminder) {
                acumulador.add(reminder.createFakeReminder())
            }
        }
        val retorn=arrayOfNulls<FakeReminder>(acumulador.size)
        acumulador.toArray(retorn)
        return retorn
    }
    fun getFakeActivityTherapy():Array<FakeTherapy?>{
        var acumulador=ArrayList<FakeTherapy>()
        for(therapy in this.therapies){
            if(therapy is ActivityTherapy) {
                acumulador.add(therapy.createFakeTherapy())
            }
        }
        val retorn=arrayOfNulls<FakeTherapy>(acumulador.size)
        acumulador.toArray(retorn)
        return retorn
    }
    fun getFakeMeasurementTherapy():Array<FakeTherapy?>{
        var acumulador=ArrayList<FakeTherapy>()
        for(therapy in this.therapies){
            if(therapy is MeasurementTherapy) {
                acumulador.add(therapy.createFakeTherapy())
            }
        }
        val retorn=arrayOfNulls<FakeTherapy>(acumulador.size)
        acumulador.toArray(retorn)
        return retorn
    }
    fun getFakeMedicationTherapy():Array<FakeTherapy?>{
        var acumulador=ArrayList<FakeTherapy>()
        for(therapy in this.therapies){
            if(therapy is MedicineTherapy) {
                acumulador.add(therapy.createFakeTherapy())
            }
        }
        val retorn=arrayOfNulls<FakeTherapy>(acumulador.size)
        acumulador.toArray(retorn)
        return retorn
    }
    override fun toString(): String {
        return "id "+id+" email "+email +" username "+username+" pasword "+pasword+" gender "+gender+" birthYear "+birthYear.toString()+" weight "+weight.toString()+" height "+height.toString()
    }

    fun clear() {
        this.reminders.clear()
        this.birthYear=0
        this.weight=0F
        this.height=0F
        this.id=""
        this.agenda.clear()
        this.therapies.clear()
        this.username=""
        this.email=""
        this.pasword=""
        this.gender=""
        this.statistics.clear()
    }

    fun deleteAllRemindersById(id: String) {
        var mList = arrayListOf<Reminder>()
        for(i in 0 until this.reminders.size){
            if(this.reminders[i].ID == id && this.reminders[i].status == ReminderStatus.TO_DO){
                mList.add(this.reminders[i])
            }
        }
        for(i in mList){
            this.reminders.remove(i)
        }
    }

    fun getTodayReminders(): ArrayList<Reminder> {
        var reminders=ArrayList<Reminder>()
        for(rem in this.reminders){
            if(rem.date.equals(LocalDate.now()))
                reminders.add(rem)
        }
        return reminders
    }

    fun getReminerBetween(from: LocalDate?, to: LocalDate?): ArrayList<Reminder> {
        var retorn=ArrayList<Reminder>()
        for(reminder in this.reminders)
            if((reminder.date.equals(from)||reminder.date.isAfter(from))&&(reminder.date.equals(to)||reminder.date.isBefore(to)))
                retorn.add(reminder)
        return retorn
    }


}