package com.example.afontgou17alumnes.mypillrecord.data.controller

import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeMeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeMedicationReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeStatistics.FakeStatistics
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

}