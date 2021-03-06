package com.example.afontgou17alumnes.mypillrecord.data.controller

import android.content.Context
import android.util.Log
import android.widget.Toast


class ControllerSharePrefs {
    //IMPRESCINDIBLE INICIALITZAR AL INICI DEL PROGRAMA AMB UN CONTEXT, DEL CONTRARI, NO FUNCIONARÀ
    //Aquest companion object s'ha d'inicialitzar des de una activity només un cop, és molt important
    //que només sigui un cop, encara no ho he fet l'inicialitzo a logIn
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
        editor.putString("ActivityTherapy",Controller.controllerJSON.getActivityTherapyJSON())
        editor.putString("MeasurementTherapy",Controller.controllerJSON.getMeasurementTherapyJSON())
        editor.putString("MedicineTherapy",Controller.controllerJSON.getMedicineTherapyJSON())
        editor.apply()
        //Log.d("therapy1",Controller.controllerJSON.getActivityTherapyJSON())
        //Log.d("therapy2",Controller.controllerJSON.getMedicineTherapyJSON())
        //Log.d("therapy3",Controller.controllerJSON.getMeasurementTherapyJSON())
        /*var prefs =context.getSharedPreferences("Mydata", Context.MODE_PRIVATE)


        Log.d("hola","uploaded")*/
    }
    fun sharedUpLoadReminders() {
        val editor = context.getSharedPreferences("Mydata", Context.MODE_PRIVATE).edit()
        editor.putString("ActivityReminder", Controller.controllerJSON.getActivityReminderJSON())
        editor.putString("MeasurementReminder", Controller.controllerJSON.getMeasurementReminderJSON())
        editor.putString("MedicationReminder", Controller.controllerJSON.getMedicineReminderJSON())
        editor.apply()
        Log.d("ActivityReminder",Controller.controllerJSON.getActivityReminderJSON())
        Log.d("MeasurementReminder",Controller.controllerJSON.getMeasurementReminderJSON())
        Log.d("MedicationReminder",Controller.controllerJSON.getMedicineReminderJSON())
    }

    fun sharedUpLoadStatistics() {
        val editor = context.getSharedPreferences("Mydata", Context.MODE_PRIVATE).edit()
        editor.putString("Statistics", Controller.controllerJSON.getStatisticsJSON())
        editor.apply()
        Log.d("Statistics",Controller.controllerJSON.getStatisticsJSON())
    }
    fun sharedUpLoadTherapy() {
        val editor = context.getSharedPreferences("Mydata", Context.MODE_PRIVATE).edit()
        editor.putString("ActivityTherapy",Controller.controllerJSON.getActivityTherapyJSON())
        editor.putString("MeasurementTherapy",Controller.controllerJSON.getMeasurementTherapyJSON())
        editor.putString("MedicineTherapy",Controller.controllerJSON.getMedicineTherapyJSON())
        editor.apply()
        Log.d("ActivityTherapy",Controller.controllerJSON.getActivityTherapyJSON())
        Log.d("MeasurementTherapy",Controller.controllerJSON.getMeasurementTherapyJSON())
        Log.d("MedicineTherapy",Controller.controllerJSON.getMedicineTherapyJSON())
    }

    fun sharedDownloadLoad(){
        Log.d("hola1","peque")
        var prefs = context.getSharedPreferences("Mydata", Context.MODE_PRIVATE)
        Log.d("hola2","peque")
        if(prefs.contains("id")){
            Log.d("hola3","peque")
            Toast.makeText(context,"Preparing Data!", Toast.LENGTH_LONG).show()
            Log.d("hola","peque")
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
            Log.d("reminder",jsonList)
            Controller.controllerJSON.setMedicineReminderFromJSON(jsonList)

            jsonList=prefs.getString("MeasurementReminder","")
            Log.d("reminder",jsonList)
            Controller.controllerJSON.setMeasurementReminderFromJSON(jsonList)

            jsonList=prefs.getString("ActivityReminder","")
            Log.d("reminder",jsonList)
            Controller.controllerJSON.setActivityReminderFromJSON(jsonList)

            var jsonList2=prefs.getString("MedicineTherapy","")
            Log.d("therapy",jsonList2)
            Controller.controllerJSON.setMedicineTherapyFronJSON(jsonList2)

            jsonList2=prefs.getString("MeasurementTherapy","")
            Log.d("Therapy",jsonList2)
            Controller.controllerJSON.setMeasurementTherapyFronJSON(jsonList2)

            jsonList2=prefs.getString("ActivityTherapy","")
            Log.d("Therapy",jsonList2)
            Controller.controllerJSON.setActivityTherapyFronJSON(jsonList2)
            //Actualitza les notificacions
            Controller.generarNextNotification()
        }
    }
    fun closeSesion(){
        /*
        * No entenc perquè si fas close session pero no tanques l'aplicació del tot, no desapareixen les share preferences
        * */
        val editor = context.getSharedPreferences("Mydata", Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
        Controller.clearUser()
    }
}