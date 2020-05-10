package com.example.afontgou17alumnes.mypillrecord.data.controller

import android.graphics.Color
import android.util.Log
import com.example.afontgou17alumnes.mypillrecord.data.model.User
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.*
import com.example.afontgou17alumnes.mypillrecord.data.model.statistics.StatisticEntry
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.ActivityTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

object Controller {
    val user = User("1","user@gmail.com", "PillRecord", "123", "Male", 1999, 50F, 160F)
    var ja_iniciat=false
    val db = FirebaseFirestore.getInstance()
    private var mAuth = FirebaseAuth.getInstance()
    val controllerJSON=ControllerJSON()//Serveix per a treballar amb els JSON
    var controllerSharePrefs=ControllerSharePrefs()//Serveix per treballar amb les share preferences

    /*fun initUserSaved(){
        controllerSharePrefs.sharedDownloadLoad()
    }

    fun savePreferences(){
        controllerSharePrefs.sharedUpLoad()
    }*/

    fun setRemindersData(){
        //user.addReminder(MeasurementReminder("Weight","kg", LocalDate.now(), LocalTime.of(LocalTime.now().hour,LocalTime.now().minute+1)))
        //user.addReminder(MedicineReminder("Ibuprofen",3,"tablet(s)", LocalDate.now(), LocalTime.of(17,0)))
        //user.addReminder(MeasurementReminder("Weight","kg",LocalDate.now(), LocalTime.of(17,0)))
        //user.addReminder(ActivityReminder("Running", 15, LocalDate.now(), LocalTime.of(18,0)))

    }

    fun getRemindersData() : ArrayList<Reminder>{
        return user.reminders
    }

    /** Return the list of dates which contains reminders*/
    fun getDatesOfReminders(): ArrayList<LocalDate> {
        val datesListOfReminders = ArrayList<LocalDate>()
        user.reminders.sortWith(Comparator { p1, p2 ->
            when {
                p1.date > p2.date -> 1
                p1.date < p2.date -> -1
                p1.time > p2.time -> 1
                p1.time < p2.time -> -1
                else -> 0
            }
        })
        for (i in user.reminders) {
            if (datesListOfReminders.contains(i.getReminderDate()))
            else datesListOfReminders.add(i.getReminderDate())
        }
        return datesListOfReminders
    }

    /** Return the list of dates and reminder in a header-row format*/
    fun getDatesAndReminders() : ArrayList<Any>{
        val listAll = ArrayList<Any>()
        user.reminders.sortWith(Comparator { p1, p2 ->
            when {
                p1.date > p2.date -> 1
                p1.date < p2.date -> -1
                p1.time > p2.time -> 1
                p1.time < p2.time -> -1
                else -> 0
            }
        })
        //Es podria substituir per cerca binaria
        for(i in user.reminders){
            if (listAll.contains(i.getReminderDate()))
            else listAll.add(i.getReminderDate())
            listAll.add(i)
        }
        return listAll
    }

    fun getRemindersByDate(date : LocalDate) : ArrayList<Reminder>{
        val remindersToday = ArrayList<Reminder>()
        user.reminders.sortWith(Comparator { p1, p2 ->
            when {
                p1.date > p2.date -> 1
                p1.date < p2.date -> -1
                p1.time > p2.time -> 1
                p1.time < p2.time -> -1
                else -> 0
            }
        })
        //Es podria substituir per cerca binaria
        for(i in user.reminders){
            if(i.getReminderDate() == date) remindersToday.add(i)
            else if (i.getReminderDate() > LocalDate.now()) break
        }
        return remindersToday
    }

    fun getRemindersByDateAndStatus(date: LocalDate, status: ReminderStatus) : ArrayList<Reminder>{
        val remindersToday = ArrayList<Reminder>()
        user.reminders.sortWith(Comparator { p1, p2 ->
            when {
                p1.date > p2.date -> 1
                p1.date < p2.date -> -1
                p1.time > p2.time -> 1
                p1.time < p2.time -> -1
                else -> 0
            }
        })
        //Es podria substituir per cerca binaria
        for(i in user.reminders){
            if(i.getReminderDate() == date && i.getReminderStatus() == status) remindersToday.add(i)
            else if (i.getReminderDate() > LocalDate.now()) break
        }
        return remindersToday
    }

    fun getGraphLineData(select: Int) : LineData {
        //Agafem les dades que ens interessen segons quin grafic muntarem
        val dataSets: MutableList<ILineDataSet> = ArrayList()
        when(select){
            0-> dataSets.add(getLineDataSet(user.statistics.weightData, "Weight", Color.YELLOW))
            1 -> dataSets.add(getLineDataSet(user.statistics.heartRateData, "Heart Rate", Color.RED))
            2 -> dataSets.add(getLineDataSet(user.statistics.arterialPressureData, "Arterial Pressure", Color.rgb(255,165,0)))
            3 ->{dataSets.add(getLineDataSet(user.statistics.glucoseBeforeData, "Glucose (before eating)", Color.MAGENTA))
                dataSets.add(getLineDataSet(user.statistics.glucoseAfterData, "Glucose (after eating)", Color.BLUE))}
            4 -> dataSets.add(getLineDataSet(user.statistics.temperatureData, "Temperature", Color.GREEN))
        }
        return LineData(dataSets)
    }

    fun getLineDataSet(values: MutableList<StatisticEntry>, label: String, color: Int) : LineDataSet{
        //Formatejem les dades per a que les pugui llegir el gràfic
        val formattedValues = ArrayList<Entry>()
        for (i in values){
            val days = ChronoUnit.DAYS.between(i.date,LocalDate.now())
            formattedValues.add(Entry(0 - days.toFloat(), i.value))
        }

        //Ordenem els valors
        formattedValues.sortWith(Comparator<Entry> { p1, p2 ->
            when {
                p1.x > p2.x -> 1
                p1.x == p2.x -> 0
                else -> -1
            }
        })

        val setComp1 = LineDataSet(formattedValues, label)
        setComp1.axisDependency = YAxis.AxisDependency.LEFT
        setComp1.color = Color.rgb(250,0,0)
        setComp1.lineWidth = 3f
        setComp1.circleRadius = 6f
        setComp1.setCircleColor(Color.rgb(250,0,0))
        setComp1.highLightColor = Color.RED
        setComp1.setDrawValues(false)

        return setComp1
    }

    fun setGender(gender:String){
        user.gender=gender
    }
    fun setHeight(height:Float){
        user.height=height
    }
    fun setWeight(weight:Float){
        user.weight=weight
    }
    fun setPassword(oldPassword:String,newPasword:String,repetedNewPasword:String){
        //S'ha de millorar el metode change pasword per a que pugui enviar errors
        user.changePasword(oldPassword,newPasword,repetedNewPasword)
    }
    fun addTherapy(therapy: Therapy){
        user.addTherapy(therapy)
        controllerSharePrefs.sharedUpLoad()
    }
    fun addReminder(reminder: Reminder){
        user.reminders.add(reminder)
        Log.d("hola:",user.reminders[user.reminders.size-1].toString())
        controllerSharePrefs.sharedUpLoad()
    }
    fun createMedicineReminder(medicine:String,dose:Int,units:String,
    date:LocalDate,time: LocalTime
    ): Reminder {
        return MedicineReminder(
            medicine,
            dose,
            units,
            date,
            time
        )
    }

    fun createActivityReminder(activity: String, duration: Int,
                               date: LocalDate, time: LocalTime
    ): Reminder {
        return ActivityReminder(
            activity,
            duration,
            date,
            time
        )
    }
    fun createMeasurementReminder(type:String,units:String,date:LocalDate,time:LocalTime,value:Float=0F): Reminder {
        return MeasurementReminder(
            type,
            units,
            date,
            time,
            value
        )
    }

    fun refreshMyAccount(email:String,username: String,gender: String, birthYear: Int, height: Float, weight: Float) {
        //mAuth=FirebaseAuth.getInstance()
        //val db = FirebaseFirestore.getInstance()
        val user2: FirebaseUser? = mAuth.currentUser
        val id =user2?.uid

        if(gender!=this.user.gender){
            val data = hashMapOf("gender" to gender.toString())
            db.collection("users").document(id!!).set(data, SetOptions.merge())
        }
        if(birthYear!=this.user.birthYear){
            val data = hashMapOf("yearBirth" to birthYear)
            db.collection("users").document(id!!).set(data, SetOptions.merge())
        }
        if(weight!=this.user.weight){
            val data = hashMapOf("weight" to weight)
            db.collection("users").document(id!!).set(data, SetOptions.merge())
        }
        if(height!=this.user.height){
            val data = hashMapOf("height" to height)
            db.collection("users").document(id!!).set(data, SetOptions.merge())
        }
        this.user.username= username
        this.user.email=email
        this.user.weight=weight
        this.user.height=height
        this.user.gender=gender
        this.user.birthYear=birthYear
    }

    fun downloadUserAccount(id: String,email:String,username: String,gender: String, birthYear: Int, height: Float, weight: Float,pasword: String) {
        this.user.username= username
        this.user.email=email
        this.user.weight=weight
        this.user.height=height
        this.user.gender=gender
        this.user.birthYear=birthYear
        this.user.pasword=pasword
        this.user.id=id
        Log.e("controller", "pwd:  ${pasword}")

    }

    fun initAgenda(JSONString:String){
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<Array<Reminder>>() {}.type

        var tutorials: Array<Reminder> = gson.fromJson(JSONString, arrayTutorialType)
        tutorials.forEachIndexed  { idx, tut -> println("> Item ${idx}:\n${tut}") }

    }

    fun createAccount(id:String,email:String, username:String, pasword:String, gender:String, yearBirth:Int, weight: Float, height: Float){
        user.id=id
        user.email=email
        user.username=username
        user.pasword=pasword
        user.gender=gender
        user.birthYear=yearBirth
        user.height=height
        user.weight=weight
    }

    fun createAccount_in_Firebase(){}//Cal fer, ara està buida

    fun deleteMeasure(type: String, value: Float, date: LocalDate) {
        user.statistics.deleteMeasure(type, value, date)

    }

    fun getReminderStatusToInt(status: ReminderStatus): Int {
        return when(status){
            ReminderStatus.DONE -> 0
            ReminderStatus.OMITTED -> 1
            ReminderStatus.TO_DO -> 2
        }
    }

    fun getIntToReminderStatus(i: Int): ReminderStatus {
        return when(i){
            0 -> ReminderStatus.DONE
            1 -> ReminderStatus.OMITTED
            2 -> ReminderStatus.TO_DO
            else -> ReminderStatus.TO_DO
        }
    }

    fun updatePassword(newPasword: String) {
        //mAuth=FirebaseAuth.getInstance()
        //val db = FirebaseFirestore.getInstance()
        val user2: FirebaseUser? = mAuth.currentUser
        val id =user2?.uid
        mAuth.currentUser!!.updatePassword(newPasword)
        user.pasword=newPasword
        val data = hashMapOf("password" to user.pasword)
        db.collection("users").document(id!!).set(data, SetOptions.merge())

    }


    fun addStaticsValueToFirebase(type: String, statisticEntry: StatisticEntry) {
        when(type){
            "weightData"-> {
                val localDate:LocalDate=statisticEntry.date
                val date: Date = java.sql.Date.valueOf(localDate.toString())
                val nestedData = hashMapOf(
                    "data" to statisticEntry.value,
                    "date" to Timestamp(date)
                )
                val doc2Data = hashMapOf(
                    "weightData" to arrayListOf(nestedData)
                )
                //val data = hashMapOf("weightData" to statisticEntry)
                //db.collection("statistics").document(user.id).set(data, SetOptions.merge())
                val docRef = db.collection("statistics").document(user.id)
                docRef.get().addOnSuccessListener { document ->
                    if (document != null ) {
                        var map= document.data as MutableMap<String, Any?>
                        Log.e("data", "Cmap${map.containsKey("weightData")}")
                        if(map.containsKey("weightData")){
                            db.collection("statistics").document(user.id)
                                .update("weightData", FieldValue.arrayUnion(nestedData))
                                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
                        } else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }.addOnFailureListener { exception ->
                    Log.e("getDatafromfirestore", "get failed with ", exception)
                }
            }
            "heartRateData"-> {
                val localDate:LocalDate=statisticEntry.date
                val date: Date = java.sql.Date.valueOf(localDate.toString())
                val nestedData = hashMapOf(
                    "data" to statisticEntry.value,
                    "date" to Timestamp(date)
                )
                val doc2Data = hashMapOf(
                    "heartRateData" to arrayListOf(nestedData)
                )
                //val data = hashMapOf("weightData" to statisticEntry)
                //db.collection("statistics").document(user.id).set(data, SetOptions.merge())
                val docRef = db.collection("statistics").document(user.id)
                docRef.get().addOnSuccessListener { document ->
                    if (document != null ) {
                        var map= document.data as MutableMap<String, Any?>
                        Log.e("data", "Cmap${map.containsKey("heartRateData")}")
                        if(map.containsKey("heartRateData")){
                            db.collection("statistics").document(user.id)
                                .update("heartRateData", FieldValue.arrayUnion(nestedData))
                                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
                        } else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }.addOnFailureListener { exception ->
                    Log.e("getDatafromfirestore", "get failed with ", exception)
                }

            }
            "arterialPressureData"-> {
                val localDate:LocalDate=statisticEntry.date
                val date: Date = java.sql.Date.valueOf(localDate.toString())
                val nestedData = hashMapOf(
                    "data" to statisticEntry.value,
                    "date" to Timestamp(date)
                )
                val doc2Data = hashMapOf(
                    "arterialPressureData" to arrayListOf(nestedData)
                )
                //val data = hashMapOf("weightData" to statisticEntry)
                //db.collection("statistics").document(user.id).set(data, SetOptions.merge())
                val docRef = db.collection("statistics").document(user.id)
                docRef.get().addOnSuccessListener { document ->
                    if (document != null ) {
                        var map= document.data as MutableMap<String, Any?>
                        Log.e("data", "Cmap${map.containsKey("arterialPressureData")}")
                        if(map.containsKey("arterialPressureData")){
                            db.collection("statistics").document(user.id)
                                .update("arterialPressureData", FieldValue.arrayUnion(nestedData))
                                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
                        } else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }.addOnFailureListener { exception ->
                    Log.e("getDatafromfirestore", "get failed with ", exception)
                }

            }
            "glucoseBeforeData"-> {
                val localDate:LocalDate=statisticEntry.date
                val date: Date = java.sql.Date.valueOf(localDate.toString())
                val nestedData = hashMapOf(
                    "data" to statisticEntry.value,
                    "date" to Timestamp(date)
                )
                val doc2Data = hashMapOf(
                    "glucoseBeforeData" to arrayListOf(nestedData)
                )
                //val data = hashMapOf("weightData" to statisticEntry)
                //db.collection("statistics").document(user.id).set(data, SetOptions.merge())
                val docRef = db.collection("statistics").document(user.id)
                docRef.get().addOnSuccessListener { document ->
                    if (document != null ) {
                        var map= document.data as MutableMap<String, Any?>
                        Log.e("data", "Cmap${map.containsKey("glucoseBeforeData")}")
                        if(map.containsKey("glucoseBeforeData")){
                            db.collection("statistics").document(user.id)
                                .update("glucoseBeforeData", FieldValue.arrayUnion(nestedData))
                                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
                        } else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }.addOnFailureListener { exception ->
                    Log.e("getDatafromfirestore", "get failed with ", exception)
                }
            }
            "glucoseAfterData"-> {
                val localDate:LocalDate=statisticEntry.date
                val date: Date = java.sql.Date.valueOf(localDate.toString())
                val nestedData = hashMapOf(
                    "data" to statisticEntry.value,
                    "date" to Timestamp(date)
                )
                val doc2Data = hashMapOf(
                    "glucoseAfterData" to arrayListOf(nestedData)
                )
                //val data = hashMapOf("weightData" to statisticEntry)
                //db.collection("statistics").document(user.id).set(data, SetOptions.merge())
                val docRef = db.collection("statistics").document(user.id)
                docRef.get().addOnSuccessListener { document ->
                    if (document != null ) {
                        var map= document.data as MutableMap<String, Any?>
                        Log.e("data", "Cmap${map.containsKey("glucoseAfterData")}")
                        if(map.containsKey("glucoseAfterData")){
                            db.collection("statistics").document(user.id)
                                .update("glucoseAfterData", FieldValue.arrayUnion(nestedData))
                                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
                        } else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }.addOnFailureListener { exception ->
                    Log.e("getDatafromfirestore", "get failed with ", exception)
                }

            }
            "temperatureData"-> {
                val localDate:LocalDate=statisticEntry.date
                val date: Date = java.sql.Date.valueOf(localDate.toString())
                val nestedData = hashMapOf(
                    "data" to statisticEntry.value,
                    "date" to Timestamp(date)
                )
                val doc2Data = hashMapOf(
                    "temperatureData" to arrayListOf(nestedData)
                )
                //val data = hashMapOf("weightData" to statisticEntry)
                //db.collection("statistics").document(user.id).set(data, SetOptions.merge())
                val docRef = db.collection("statistics").document(user.id)
                docRef.get().addOnSuccessListener { document ->
                    if (document != null ) {
                        var map= document.data as MutableMap<String, Any?>
                        Log.e("data", "Cmap${map.containsKey("temperatureData")}")
                        if(map.containsKey("temperatureData")){
                            db.collection("statistics").document(user.id)
                                .update("temperatureData", FieldValue.arrayUnion(nestedData))
                                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
                        } else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }.addOnFailureListener { exception ->
                    Log.e("getDatafromfirestore", "get failed with ", exception)
                }

            }
        }
    }

    fun clearUser() {
        user.clear()
    }

    fun addTherapy__CreateReminders(therapy: Therapy) {
        this.addTherapy(therapy)
        therapy.createReminders()
    }
    fun getLocalDate(year:Int,month:Int,day:Int):LocalDate{
        var month_str=month.toString()
        var day_str=day.toString()
        var year_str=year.toString()
        if(month<10)
            month_str="0"+month_str
        if(day<10)
            day_str="0"+day_str
        return LocalDate.parse(year_str+"-"+month_str+"-"+day_str)
    }
}