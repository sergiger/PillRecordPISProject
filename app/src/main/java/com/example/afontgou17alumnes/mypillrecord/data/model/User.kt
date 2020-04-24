package com.example.afontgou17alumnes.mypillrecord.data.model

import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import android.util.Log
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeMeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeMedicationReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeReminder

open class User{
    var email:String
    var username:String
    var pasword:String
        set(value) {
            if (value.length>6) field=value //Aqui faig que la contrassenya hagi de ser minim de 6 digits, però s'ha de modificar
        }
    var gender:String
    var birthYear :Int
    var weight:Float
    var height:Float
    var therapies= ArrayList<Therapy>()
    var reminders=ArrayList<Reminder>()
    var agenda=Agenda()

    constructor(email:String, username:String, pasword:String, gender:String,
                birthYear:Int, weight:Float, height:Float){
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
        this.reminders.add(reminder)
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

    fun getNextReminder():Reminder{
        var retorn:Reminder= MeasurementReminder("Weight","kg", LocalDate.now(), LocalTime.now())
        for(reminder in reminders){
            if(reminder.getMilisFromNow()>Calendar.getInstance().timeInMillis)
                if(!reminder.isDone()){
                    retorn=reminder
                    break
                }
        }
        return retorn
    }
    fun areThereReminders():Boolean{
        var retorn=false
        for(reminder in reminders){
            Log.d("hh",reminder.time.toString())
            if(reminder.getMilisFromNow()>Calendar.getInstance().timeInMillis) {
                Log.d("hh2",reminder.time.toString())
                if (!reminder.isDone()) {
                    Log.d("hh3",reminder.time.toString())
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


}