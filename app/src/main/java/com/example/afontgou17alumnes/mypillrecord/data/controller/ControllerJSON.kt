package com.example.afontgou17alumnes.mypillrecord.data.controller

import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder.FakeActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder.FakeMeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder.FakeMedicationReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeReminder.FakeReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeStatistics.FakeStatistics
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeActivityTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeMeasurementTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeMedicineTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.supportClasses.fakeTherapy.FakeTherapy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ControllerJSON {
    fun getStatisticsJSON():String{
        return Gson().toJson(Controller.user.statistics.createFakeStatistics())
    }

    fun getActivityReminderJSON():String{
        return Gson().toJson(Controller.user.getFakeActivityReminders())
    }
    fun getMeasurementReminderJSON():String{
        return Gson().toJson(Controller.user.getFakeMeasurementReminders())
    }
    fun getMedicineReminderJSON():String{
        return Gson().toJson(Controller.user.getFakeMedicationReminders())
    }

    fun getActivityTherapyJSON():String{
        return Gson().toJson(Controller.user.getFakeActivityTherapy())
    }
    fun getMeasurementTherapyJSON():String{
        return Gson().toJson(Controller.user.getFakeMeasurementTherapy())
    }
    fun getMedicineTherapyJSON():String{
        return Gson().toJson(Controller.user.getFakeMedicationTherapy())
    }

    fun setStatisticssFromJSON(json:String){
        val fakeStatistics: FakeStatistics = Gson().fromJson(json, FakeStatistics::class.java)
        Controller.user.statistics=fakeStatistics.createRealStatistics()
    }

    fun setActivityReminderFromJSON(json:String){
        val gson = Gson()
        var tipusArray: Type

        if(json!=""){
            var tipusArray = object : TypeToken<Array<FakeActivityReminder>>() {}.type
            var reminder_list1: Array<FakeReminder> = gson.fromJson(json, tipusArray)
            for(reminder in reminder_list1){
                Controller.user.reminders.add(reminder.createRealReminder())
            }
        }
    }
    fun setActivityReminderFromJSONfollower(json:String){
        val gson = Gson()
        var tipusArray: Type

        if(json!=""){
            var tipusArray = object : TypeToken<Array<FakeActivityReminder>>() {}.type
            var reminder_list1: Array<FakeReminder> = gson.fromJson(json, tipusArray)
            for(reminder in reminder_list1){
                Controller.temporalReminders.add(reminder.createRealReminder())
            }
        }
    }
    fun setMeasurementReminderFromJSON(json:String){
        val gson = Gson()
        var tipusArray: Type

        if(json!=""){
            tipusArray = object : TypeToken<Array<FakeMeasurementReminder>>() {}.type
            var reminder_list2 : Array<FakeReminder> = gson.fromJson(json, tipusArray)
            for(reminder in reminder_list2){
                Controller.user.reminders.add(reminder.createRealReminder())
            }
        }
    }
    fun setMeasurementReminderFromJSONfollower(json:String){
        val gson = Gson()
        var tipusArray: Type

        if(json!=""){
            tipusArray = object : TypeToken<Array<FakeMeasurementReminder>>() {}.type
            var reminder_list2 : Array<FakeReminder> = gson.fromJson(json, tipusArray)
            for(reminder in reminder_list2){
                Controller.temporalReminders.add(reminder.createRealReminder())
            }
        }
    }
    fun setMedicineReminderFromJSON(json:String){
        val gson = Gson()
        var tipusArray: Type

        if(json!=""){
            tipusArray = object : TypeToken<Array<FakeMedicationReminder>>() {}.type
            var reminder_list3:Array<FakeReminder> = gson.fromJson(json, tipusArray)
            for(reminder in reminder_list3){
                Controller.user.reminders.add(reminder.createRealReminder())
            }
        }
    }
    fun setMedicineReminderFromJSONfollowers(json:String){
        val gson = Gson()
        var tipusArray: Type

        if(json!=""){
            tipusArray = object : TypeToken<Array<FakeMedicationReminder>>() {}.type
            var reminder_list3:Array<FakeReminder> = gson.fromJson(json, tipusArray)
            for(reminder in reminder_list3){
                Controller.temporalReminders.add(reminder.createRealReminder())
            }
        }
    }

    fun setActivityTherapyFronJSON(json:String){
        val gson = Gson()
        var tipusArray: Type

        if(json!=""){
            tipusArray = object : TypeToken<Array<FakeActivityTherapy>>() {}.type
            var reminder_list3:Array<FakeTherapy> = gson.fromJson(json, tipusArray)
            for(reminder in reminder_list3){
                Controller.user.addTherapy(reminder.createRealTherapy())
            }
        }
    }
    fun setMeasurementTherapyFronJSON(json:String){
        val gson = Gson()
        var tipusArray: Type

        if(json!=""){
            tipusArray = object : TypeToken<Array<FakeMeasurementTherapy>>() {}.type
            var reminder_list3:Array<FakeTherapy> = gson.fromJson(json, tipusArray)
            for(reminder in reminder_list3){
                Controller.user.addTherapy(reminder.createRealTherapy())
            }
        }
    }
    fun setMedicineTherapyFronJSON(json:String){
        val gson = Gson()
        var tipusArray: Type

        if(json!=""){
            tipusArray = object : TypeToken<Array<FakeMedicineTherapy>>() {}.type
            var reminder_list3:Array<FakeTherapy> = gson.fromJson(json, tipusArray)
            for(reminder in reminder_list3){
                Controller.user.addTherapy(reminder.createRealTherapy())
            }
        }
    }

}