package com.example.afontgou17alumnes.mypillrecord.data.controller

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.ui.register.activity_Register4
import com.google.gson.Gson


class ControllerSharePrefs {
    //IMPRESCINDIBLE INICIALITZAR AL INICI DEL PROGRAMA AMB UN CONTEXT, DEL CONTRARI, NO FUNCIONARÀ
    //Aquest companion object s'ha d'inicialitzar des de una activity només un cop, és molt important
    //que només sigui un cop, encara no ho he fet
    companion object {

        private lateinit var context: Context

        fun setContext(con: Context) {
            context=con
        }
    }
    fun sharedUpLoad(){
        val editor = context.getSharedPreferences("Mydata", Context.MODE_PRIVATE).edit()
        editor.putString("id",Controller.user.id)
        editor.putString("email",Controller.user.email)
        editor.putString("pasword",Controller.user.pasword)
        //Ara hauriem d'anar a la base de dades i agafar la resta, jo ara posaré valors fixes, però el pròxim pas és conectar-ho amb el firebase
        editor.putString("username",Controller.user.username)
        editor.putString("gender",Controller.user.gender)
        editor.putString("yearBirth",Controller.user.birthYear.toString())
        editor.putString("weight",Controller.user.weight.toString())
        editor.putString("height",Controller.user.height.toString())
        editor.putString("ActivityReminder", Controller.controllerJSON.getActivityReminderJSON())
        editor.putString("MeasurementReminder", Controller.controllerJSON.getMeasurementReminderJSON())
        editor.putString("MedicationReminder", Controller.controllerJSON.getMedicineReminderJSON())
        editor.putString("Statistics", Controller.controllerJSON.getStatisticsJSON())
        editor.apply()
        //Log.d("hola",Controller.controllerJSON.getStatisticsJSON())
        /*var prefs =context.getSharedPreferences("Mydata", Context.MODE_PRIVATE)


        Log.d("hola","uploaded")*/
    }
    fun sharedDownloadLoad(){
        var prefs = context.getSharedPreferences("Mydata", Context.MODE_PRIVATE)
        //Log.d("hola",getSharedPreferences("Mydata", Context.MODE_PRIVATE).contains("email").toString())
        if(prefs.contains("id")){
            //Log.d("hola",getSharedPreferences("Mydata", Context.MODE_PRIVATE).contains("email").toString())
            Toast.makeText(context,"Preparing Data!", Toast.LENGTH_LONG).show()
            //Log.d("hola",getSharedPreferences("Mydata", Context.MODE_PRIVATE).contains("email").toString())
            Controller.user.id=prefs.getString("id","")
            Controller.user.email=prefs.getString("email","")

            Controller.user.pasword=  prefs.getString("pasword","")

            Controller.user.username = prefs.getString("username","")

            Controller.user.gender = prefs.getString("gender","")

            Controller.user.birthYear= prefs.getString("yearBirth","").toInt()

            Controller.user.weight= prefs.getString("weight","").toFloat()

            Controller.user.height= prefs.getString("height","").toFloat()

            //He separat els reminders en 3 perque no sabia com passar de JSON a array de reminders amb diferents constructors

            val fakeStatisticsJSON=prefs.getString("Statistics","")
            Log.d("statisticsDownload",fakeStatisticsJSON)
            Controller.controllerJSON.setStatisticssFromJSON(fakeStatisticsJSON)

            var jsonList=prefs.getString("MedicationReminder","")
            Log.d("measurement",jsonList)
            Controller.controllerJSON.setMedicineReminderFromJSON(jsonList)

            jsonList=prefs.getString("MeasurementReminder","")
            Log.d("measurement",jsonList)
            Controller.controllerJSON.setMeasurementReminderFromJSON(jsonList)

            jsonList=prefs.getString("ActivityReminder","")
            Log.d("measurement",jsonList)
            Controller.controllerJSON.setActivityReminderFromJSON(jsonList)





        }
    }
    fun closeSesion(){
        val editor = context.getSharedPreferences("Mydata", Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }
}