package com.example.afontgou17alumnes.mypillrecord.data.model

open class User{
    val email:String
        get()=field
    val username:String
        get() =field
    var pasword:String
        get() =field
        set(value) {
            if (value.length>6) field=value //Aqui faig que la contrassenya hagi de ser minim de 6 digits, però s'ha de modificar
        }
    var gender:String
        get() =field
        set(value) {field=value}
    var birthYear :Int
        get() =field
        set(value) {field=value}
    var weight:Float
        get() =field
        set(value){field=value}
    var height:Float
        get() =field
        set(value){field=value}
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



}