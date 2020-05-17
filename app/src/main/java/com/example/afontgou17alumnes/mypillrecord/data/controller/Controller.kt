package com.example.afontgou17alumnes.mypillrecord.data.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.data.model.User
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.*
import com.example.afontgou17alumnes.mypillrecord.data.model.statistics.StatisticEntry
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import com.example.afontgou17alumnes.mypillrecord.notifications.NotificationUtils
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
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

object Controller {
    val user = User("1","user@gmail.com", "PillRecord", "123", "Male", 1999, 50F, 160F)
    var ja_iniciat=false
    val db = FirebaseFirestore.getInstance()
    private var mAuth = FirebaseAuth.getInstance()
    val controllerJSON=ControllerJSON()//Serveix per a treballar amb els JSON
    var controllerSharePrefs=ControllerSharePrefs()//Serveix per treballar amb les share preferences
    var connected = false
    var IShareTo = mutableMapOf<String,String>()
    var SbShareToMe = mutableMapOf<String,String>()
    var app_iniciada=false
    var main_activity_fragment=0
    /*fun initUserSaved(){
        controllerSharePrefs.sharedDownloadLoad()
    }

    fun savePreferences(){
        controllerSharePrefs.sharedUpLoad()
    }*/
    private lateinit var main: Activity

    fun setContext(con: Activity) {
        main=con
    }
    private lateinit var wait: Activity

    fun setContext_wait(con: Activity) {
        wait=con
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

    /** Return the historic of dates and reminders in a header-row format*/
    fun getHistoricDatesAndReminders() : ArrayList<Any>{
        val listAll = ArrayList<Any>()
        user.reminders.sortWith(Comparator { p1, p2 ->
            when {
                p1.date > p2.date -> -1
                p1.date < p2.date -> 1
                p1.time > p2.time -> 1
                p1.time < p2.time -> -1
                else -> 0
            }
        })
        //Es podria substituir per cerca binaria
        for (i in user.reminders) {
            if (i.getReminderDate() < LocalDate.now()) { // Historico
                if (listAll.contains(i.getReminderDate()))
                else listAll.add(i.getReminderDate())
                listAll.add(i)
            }
        }
        return listAll
    }
    /** Return the plan of dates and reminders in a header-row format*/
    fun getPlannedDatesAndReminders() : ArrayList<Any>{
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
        for (i in user.reminders) {
            if (i.getReminderDate() >= LocalDate.now()) { // Planned
                if (listAll.contains(i.getReminderDate()))
                else listAll.add(i.getReminderDate())
                listAll.add(i)
            }
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
            else if (i.getReminderDate() > date) break
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
            0-> dataSets.add(getLineDataSet(user.statistics.weightData, "Weight", Color.RED))
            1 -> dataSets.add(getLineDataSet(user.statistics.heartRateData, "Heart Rate", Color.RED))
            2 -> dataSets.add(getLineDataSet(user.statistics.arterialPressureData, "Arterial Pressure", Color.RED))
            3 ->{dataSets.add(getLineDataSet(user.statistics.glucoseBeforeData, "Glucose (before eating)", Color.RED))
                dataSets.add(getLineDataSet(user.statistics.glucoseAfterData, "Glucose (after eating)", Color.rgb(60,120,173)))}
            4 -> dataSets.add(getLineDataSet(user.statistics.temperatureData, "Temperature", Color.RED))
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
        setComp1.color = color
        setComp1.lineWidth = 3f
        setComp1.circleRadius = 6f
        setComp1.setCircleColor(color)
        setComp1.highLightColor = color
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
        TherapiesToFirebase()
    }
    fun addReminder(reminder: Reminder){
        user.reminders.add(reminder)
        Log.d("hola:",user.reminders[user.reminders.size-1].toString())
        controllerSharePrefs.sharedUpLoad()
        RemindersToFirebase()
        generarNextNotification()
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
        controllerSharePrefs.sharedUpLoad()

    }
//STATISTICS___________________________________________________________________________________________________________________
    fun StatisticToFirebase() {
        val statistics = controllerJSON.getStatisticsJSON()

        val nestedData = hashMapOf(
            "data" to statistics
        )
        val docRef = db.collection("statistics").document(user.id)
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
            } else {
                db.collection("statistics").document(user.id).set(nestedData, SetOptions.merge())
            }
        }
    }

    fun FirabasetoStatics(){
        val docRef = db.collection("statistics").document(user.id)
        val control = Controller.user
        Log.e("control", "control : $control")
        docRef.get().addOnSuccessListener { document ->
                if (document.data != null) {
                    Log.d("statistics", "DocumentSnapshot data: ${document.data}")
                    var map= document.data as MutableMap<String, Any?>
                    if(map.containsKey("data")){
                        var data =map["data"] as String
                        controllerJSON.setStatisticssFromJSON(data)
                        controllerSharePrefs.sharedUpLoadStatistics()
                    }
                } else {
                    Log.d("statistics", "No such document")

                }
            }
            .addOnFailureListener { exception ->
                Log.d("statistics", "get failed with ", exception)
            }
        Log.e("USER INSIDE firebase to statics", "get failed with ${user.id}")
    }
    //THERAPIESS___________________________________________________________________________________________________________________
    fun TherapiesToFirebase() {
        val Therapy1 = controllerJSON.getActivityTherapyJSON()
        val Therapy2 = controllerJSON.getMeasurementTherapyJSON()
        val Therapy3 = controllerJSON.getMedicineTherapyJSON()

        val nestedData = hashMapOf(
            "Activity" to Therapy1,
            "Measurement" to Therapy2,
            "Medicine" to Therapy3
        )
        val docRef = db.collection("therapies").document(user.id)
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                db.collection("therapies").document(user.id).set(nestedData, SetOptions.merge())
            } else {
                db.collection("therapies").document(user.id).set(nestedData, SetOptions.merge())
            }
        }
    }

    fun FirebasetoTherapies(){
        val docRef = db.collection("therapies").document(user.id)
        val control = Controller.user
        Log.e("control", "control : $control")
        docRef.get().addOnSuccessListener { document ->
            if (document.data != null) {
                Log.d("therapies", "DocumentSnapshot data: ${document.data}")
                var map= document.data as MutableMap<String, Any?>
                if(map.containsKey("Activity")){
                    var data1 =map["Activity"] as String
                    controllerJSON.setActivityTherapyFronJSON(data1)
                }
                if(map.containsKey("Measurement")){
                    var data2 =map["Measurement"] as String
                    controllerJSON.setMeasurementTherapyFronJSON(data2)
                }
                if(map.containsKey("Medicine")){
                    var data3 =map["Medicine"] as String
                    controllerJSON.setMedicineTherapyFronJSON(data3)
                }
                controllerSharePrefs.sharedUpLoadTherapy()

            } else {
                Log.d("therapies", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("therapies", "get failed with ", exception)
            }
        Log.e("USER INSIDE firebase to statics", "get failed with ${user.id}")
    }
    //REMINDERS___________________________________________________________________________________________________________________
    fun RemindersToFirebase() {
        val Reminders1 = controllerJSON.getActivityReminderJSON()
        val Reminders2 = controllerJSON.getMeasurementReminderJSON()
        val Reminders3 = controllerJSON.getMedicineReminderJSON()

        val nestedData = hashMapOf(
            "Activity" to Reminders1,
            "Measurement" to Reminders2,
            "Medicine" to Reminders3
        )
        val docRef = db.collection("reminders").document(user.id)
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                db.collection("reminders").document(user.id).set(nestedData, SetOptions.merge())
            } else {
                db.collection("reminders").document(user.id).set(nestedData, SetOptions.merge())
            }
        }
    }

    fun FirebasetoReminders(){
        val docRef = db.collection("reminders").document(user.id)
        val control = Controller.user
        Log.e("control", "control : $control")
        docRef.get().addOnSuccessListener { document ->
            if (document.data != null) {
                Log.d("reminders", "DocumentSnapshot data: ${document.data}")
                var map= document.data as MutableMap<String, Any?>
                if(map.containsKey("Activity")){
                    var data1 =map["Activity"] as String
                    controllerJSON.setActivityReminderFromJSON(data1)
                }
                if(map.containsKey("Measurement")){
                    var data2 =map["Measurement"] as String
                    controllerJSON.setMeasurementReminderFromJSON(data2)
                }
                if(map.containsKey("Medicine")){
                    var data3 =map["Medicine"] as String
                    controllerJSON.setMedicineReminderFromJSON(data3)
                }
                controllerSharePrefs.sharedUpLoadReminders()

                //Actualitza les notificacions
                generarNextNotification()

            } else {
                Log.d("reminders", "No such document")
            }
            //Refresh llista inici today
            wait.finish()
            val intent = Intent(main, MainActivity::class.java)
            startActivity(main,intent,null)
        }
            .addOnFailureListener { exception ->
                Log.d("reminders", "get failed with ", exception)
            }
        Log.e("USER INSIDE firebase to statics", "get failed with ${user.id}")
    }
    //INTERNET??__________________________________________________________

    fun internet(context: Context):Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        connected = isConnected
        return isConnected
    }


    fun downloadDataFromFirebaseThinksFromUser(){//download general all data from firebase
        controllerSharePrefs.sharedUpLoad()
        FirabasetoStatics()
        FirebasetoTherapies()
        FirebasetoReminders()
        //team--------------------------------
        Controller.sharerealtimedata()
        getFromFirebaseshareData()

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

    fun share_to(new_email: String,context:Context){
        //val db = FirebaseFirestore.getInstance()
        val users = db.collection("users").whereEqualTo("email", new_email).get()
        db.collection("users")
            .whereEqualTo("email", new_email)
            .get()
            .addOnSuccessListener { documents ->
                //Log.e("DOCUMENTS EMPTY?", documents.isEmpty.toString())
                if(documents.isEmpty){
                    Toast.makeText(context,"Email does not exist", Toast.LENGTH_SHORT).show()
                }
                else{
                    for (document in documents) {
                        Log.e("DOCUMENTS", "${document.id} => ${document.data}")
                        // configurant la base de dàdes.--------------------------------------------
                        //introduim els pids en els documents
                        val destinatari = document.data["uid"]
                        val jo = Controller.user.id
                        //Log.e("uid", "${destinatari}")
                        val emaildell=document.data["email"]

                        val toMe = hashMapOf(
                            "IShareTo" to arrayListOf(destinatari,document.data["email"])
                        )
                        val toHim = hashMapOf(
                            "SbShareToMe" to arrayListOf(jo,user.email)
                        )
                        //jo
                        val docRef = Controller.db.collection("team").document(user.id)
                        docRef.get().addOnSuccessListener { document ->
                            if (document.data != null) {
                                docRef.update("IShareTo", FieldValue.arrayUnion(destinatari,emaildell))
                                //docRef.set(toMe, SetOptions.merge())
                            }else{
                                docRef.set(toMe, SetOptions.merge())
                            }
                        }
                        //ell
                        val docRef2 = Controller.db.collection("team").document(destinatari.toString())
                        docRef2.get().addOnSuccessListener { document ->
                            if (document.data != null) {
                                docRef2.update("SbShareToMe", FieldValue.arrayUnion(jo,user.email))
                                //docRef2.set(toHim, SetOptions.merge())
                            }else{
                                docRef2.set(toHim, SetOptions.merge())
                            }
                        }

                        // ------------------------------------------------------------------------
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("DOCUMENTS", "Error getting documents: ", exception)
            }
    }

    fun sharerealtimedata() {
        val docRef = db.collection("team").document(user.id)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("snapshot", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.e("SNAPSHOT-----------", "Current data: ${snapshot.data}")
                val map = snapshot.data as MutableMap<String, Any?>
                if(map.containsKey("IShareTo")){
                    var data =map["IShareTo"] as ArrayList<*>
                    //Log.e("team", data.toString())
                    var map = mutableMapOf<String,String>()
                    for (i in 0..data.size -1  step 2) {
                        //println(i)
                        map.put(data[i+1] as String,data[i] as String)
                    }
                    IShareTo = map
                    Log.d("IShareTo:", "DocumentSnapshot data: ${IShareTo.toString()}")
                }
                if(map.containsKey("SbShareToMe")){
                    var data =map["SbShareToMe"] as ArrayList<*>
                    //Log.e("team", data.toString())
                    var map = mutableMapOf<String,String>()
                    for (i in 0..data.size -1  step 2) {
                        //println(i)
                        map.put(data[i+1] as String,data[i] as String)
                    }
                    SbShareToMe = map
                    Log.d("SbShareToMe:", "DocumentSnapshot data: ${SbShareToMe.toString()}")
                }

            } else {
                Log.d("snapshot", "Current data: null")
            }
        }
    }



    fun getFromFirebaseshareData(){
        val docRef = db.collection("team").document(user.id)
        docRef.get().addOnSuccessListener { document ->
            if (document.data != null) {
                Log.d("team:", "DocumentSnapshot data: ${document.data}")
                var map= document.data as MutableMap<String, Any?>
                if(map.containsKey("IShareTo")){
                    var data =map["IShareTo"] as ArrayList<*>
                    //Log.e("team", data.toString())
                    var map = mutableMapOf<String,String>()
                    for (i in 0..data.size -1  step 2) {
                        //println(i)
                        map.put(data[i+1] as String,data[i] as String)
                    }
                    IShareTo = map
                    Log.d("IShareTo:", "DocumentSnapshot data: ${IShareTo.toString()}")
                }
                if(map.containsKey("SbShareToMe")){
                    var data =map["SbShareToMe"] as ArrayList<*>
                    //Log.e("team", data.toString())
                    var map = mutableMapOf<String,String>()
                    for (i in 0..data.size -1  step 2) {
                        //println(i)
                        map.put(data[i+1] as String,data[i] as String)
                    }
                    SbShareToMe = map
                    Log.d("SbShareToMe:", "DocumentSnapshot data: ${SbShareToMe.toString()}")
                }
            } else {
                Log.d("team", "No such document")

            }
        }
            .addOnFailureListener { exception ->
                Log.d("team", "get failed with ", exception)
            }
    }
    fun deleteshareto(email :String,vid:String){
        Log.e("DELETE:", " ${vid} email: ${email}")
        //jo
        val deleteArray = db.collection("team").document(user.id)
        deleteArray.update("IShareTo", FieldValue.arrayRemove(vid,email))
        //l'altre
        val deleteArray2 = db.collection("team").document(vid)
        deleteArray2.update("SbShareToMe", FieldValue.arrayRemove(user.id,user.email))
    }
    fun deletefollowing(email :String,vid:String){
        Log.e("DELETE:", " ${vid} email: ${email}")
        //jo
        val deleteArray = db.collection("team").document(user.id)
        deleteArray.update("SbShareToMe", FieldValue.arrayRemove(vid,email))
        //l'altre
        val deleteArray2 = db.collection("team").document(vid)
        deleteArray2.update("IShareTo", FieldValue.arrayRemove(user.id,user.email))
    }


    //Notifications

    fun generarNextNotification(){
        if (this.user.areThereReminders()){
            val mNotificationTime = this.user.getNextReminder().getMilisFromNow()
            NotificationUtils().setNotification(mNotificationTime, main)
        }
        else{
            Toast.makeText(this.main,"There are no reminders",Toast.LENGTH_LONG).show()
        }
    }
    fun deleteTherapyByID(id : String){
        val t : Therapy
        for (therapy in this.user.therapies){
            if(therapy.id == id){
                t = therapy
                this.user.therapies.remove(t)
                break
            }
        }
    }

    fun clearRemindersAndTherapysFromFirebaseAndShare(){
        user.reminders.clear()
        user.therapies.clear()
        RemindersToFirebase()
        TherapiesToFirebase()
    }


}