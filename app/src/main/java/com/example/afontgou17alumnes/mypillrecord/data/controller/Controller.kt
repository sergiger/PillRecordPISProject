package com.example.afontgou17alumnes.mypillrecord.data.controller

import android.graphics.Color
import android.util.Log
import com.example.afontgou17alumnes.mypillrecord.data.model.*
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


object Controller {
    val user = User("1","user@gmail.com", "PillRecord", "123", "Male", 1999, 50F, 160F)
    var ja_iniciat=false
    val db = FirebaseFirestore.getInstance()
    private var mAuth = FirebaseAuth.getInstance()



    fun initUserSaved(){
        /*this.user.weight=SharedApp.prefs.weight
        this.user.height=SharedApp.prefs.height
        this.user.birthYear=SharedApp.prefs.yearBirth
        this.user.username=SharedApp.prefs.username
        //this.user.email=SharedApp.prefs.email
        this.user.gender=SharedApp.prefs.gender
        this.user.pasword=SharedApp.prefs.pasword*/

    }

    fun savePreferences(){

    }

    fun setRemindersData(){
        //user.addReminder(MeasurementReminder("Weight","kg", LocalDate.now(), LocalTime.of(LocalTime.now().hour,LocalTime.now().minute+1)))
        //user.addReminder(MedicineReminder("Ibuprofen",3,"tablet(s)", LocalDate.now(), LocalTime.of(17,0)))
        //user.addReminder(MeasurementReminder("Weight","kg",LocalDate.now(), LocalTime.of(17,0)))
        //user.addReminder(ActivityReminder("Running", 15, LocalDate.now(), LocalTime.of(18,0)))

    }

    fun getRemindersData() : ArrayList<Reminder>{
        return user.reminders
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

    fun setStatisticsData(){
        val data1 = arrayOf<StatisticEntry>(
            StatisticEntry(80F, LocalDate.of(2020, 3, 12)),
            StatisticEntry(81F, LocalDate.of(2020, 3, 13)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 14)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 15)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 16)),
            StatisticEntry(81F, LocalDate.of(2020, 3, 17)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 18)),
            StatisticEntry(84F, LocalDate.of(2020, 3, 19)),
            StatisticEntry(86F, LocalDate.of(2020, 3, 20)),
            StatisticEntry(85F, LocalDate.of(2020, 3, 21)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 22)),
            StatisticEntry(81F, LocalDate.of(2020, 3, 23)),
            StatisticEntry(84F, LocalDate.of(2020, 3, 24)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 25)),
            StatisticEntry(85F, LocalDate.of(2020, 3, 26)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 27)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 28)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 29)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 30)),
            StatisticEntry(85F, LocalDate.of(2020, 3, 30)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 31)),
            StatisticEntry(84F, LocalDate.of(2020, 4, 1)),
            StatisticEntry(81F, LocalDate.of(2020, 4, 2)),
            StatisticEntry(85F, LocalDate.of(2020, 4, 3))
        )

        val data2 = arrayOf<StatisticEntry>(
            StatisticEntry(85F, LocalDate.of(2020, 3, 12)),
            StatisticEntry(86F, LocalDate.of(2020, 3, 13)),
            StatisticEntry(84F, LocalDate.of(2020, 3, 14)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 15)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 16)),
            StatisticEntry(81F, LocalDate.of(2020, 3, 17)),
            StatisticEntry(89F, LocalDate.of(2020, 3, 18)),
            StatisticEntry(87F, LocalDate.of(2020, 3, 19)),
            StatisticEntry(85F, LocalDate.of(2020, 3, 20)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 21)),
            StatisticEntry(84F, LocalDate.of(2020, 3, 22)),
            StatisticEntry(89F, LocalDate.of(2020, 3, 23)),
            StatisticEntry(86F, LocalDate.of(2020, 3, 24)),
            StatisticEntry(80F, LocalDate.of(2020, 3, 25)),
            StatisticEntry(82F, LocalDate.of(2020, 3, 26)),
            StatisticEntry(83F, LocalDate.of(2020, 3, 27)),
            StatisticEntry(88F, LocalDate.of(2020, 3, 28)),
            StatisticEntry(84F, LocalDate.of(2020, 3, 29)),
            StatisticEntry(89F, LocalDate.of(2020, 3, 30)),
            StatisticEntry(87F, LocalDate.of(2020, 3, 31)),
            StatisticEntry(85F, LocalDate.of(2020, 4, 1)),
            StatisticEntry(86F, LocalDate.of(2020, 4, 2)),
            StatisticEntry(83F, LocalDate.of(2020, 4, 3))
        )
        user.statistics.weightData.addAll(data1)
        user.statistics.heartRateData.addAll(data2)
        user.statistics.arterialPressureData.addAll(data1)
        user.statistics.glucoseAfterData.addAll(data2)
        user.statistics.glucoseBeforeData.addAll(data1)
        user.statistics.temperatureData.addAll(data2)
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
    }
    fun addReminder(reminder: Reminder){
        user.addReminder(reminder)
        Log.d("hola:",user.reminders[user.reminders.size-1].toString())
    }
    fun createMedicineReminder(medicine:String,dose:Int,units:String,
    date:LocalDate,time: LocalTime
    ):Reminder{
        return MedicineReminder(medicine,dose,units,date,time)
    }

    fun createActivityReminder(activity: String, duration: Int,
                               date: LocalDate, time: LocalTime
    ): Reminder {
        return ActivityReminder(activity,duration,date,time)
    }
    fun createMeasurementReminder(type:String,units:String,date:LocalDate,time:LocalTime,value:Float=0F):Reminder{
        return MeasurementReminder(type,units,date,time,value)
    }

    fun refreshMyAccount(email:String,username: String,gender: String, birthYear: Int, height: Float, weight: Float) {
        //mAuth=FirebaseAuth.getInstance()
        //val db = FirebaseFirestore.getInstance()
        val user2: FirebaseUser? = mAuth.getCurrentUser()
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

    fun getIntToReminderStatus(i: Int): ReminderStatus{
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
        val user2: FirebaseUser? = mAuth.getCurrentUser()
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
                        }
                        else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }?.addOnFailureListener { exception ->
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
                        }
                        else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }?.addOnFailureListener { exception ->
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
                        }
                        else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }?.addOnFailureListener { exception ->
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
                        }
                        else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }?.addOnFailureListener { exception ->
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
                        }
                        else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }?.addOnFailureListener { exception ->
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
                        }
                        else{
                            db.collection("statistics").document(user.id).set(doc2Data,SetOptions.merge())
                            Log.e("setData", "Creem arraylist")
                        }

                    } else {
                        db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
                        Log.e("set data", "creem col·leció")
                    }
                }?.addOnFailureListener { exception ->
                    Log.e("getDatafromfirestore", "get failed with ", exception)
                }

            }
        }
    }
}