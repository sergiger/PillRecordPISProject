package com.example.afontgou17alumnes.mypillrecord.ui.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.ui.register.activity_Register4
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity__register4.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    lateinit var pDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProgressDialogInit()
        mAuth=FirebaseAuth.getInstance()
        setContentView(R.layout.activity_login)
        /*
        val bundle:Bundle? = intent.extras
        val actions = bundle?.get("type_of_action")//Això ens permet accedir al shared preferences, potser és una manera molt cutre, però és la única que consegueixo que funcioni
        if(actions!=null && actions=="Save_Share_and_go_back")
            sharedUpLoad_and_go_back()
        else if(actions!=null && actions=="close_sesion")
            closeSesion()
        else if(actions!=null && actions=="Save_share_Create_Account_Go_Home")
            createAccount()
        else
            sharedDownloadLoad()

         */

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        login.setOnClickListener {
            val email =username.text.toString().trim()
            val pwd = password.text.toString().trim()

            if(email.isEmpty() and pwd.isEmpty()){
                username.setError("Email is Required")
                password.setError("Password is Required")
            }
            else if(TextUtils.isEmpty(email)){
                username.setError("Email is Required")
            }
            else if(TextUtils.isEmpty(pwd)){
                password.setError("Password is Required")
            }
            else{
                ProgressDialogEnable()
                mAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("login", "signInWithEmail:success")
                            val user = mAuth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user. errors from firebase
                            try {
                                throw task.exception!!
                            }  catch (e: FirebaseAuthInvalidUserException) {
                                username.setError(e.message)
                                username.requestFocus()
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                password.setError(e.message)
                                password.requestFocus()
                            }catch (e: Exception) {
                                Log.e("register fail :", e.message)
                            }

                            Log.w("Sign in","signInWithEmail:failure",task.exception)
                            Toast.makeText(this,"Authentication failed.",Toast.LENGTH_SHORT).show()
                            Log.w("login", "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }
                        // ...
                    }
            }
        }
        btn_register.setOnClickListener {

            val intent = Intent(this@LoginActivity, activity_Register4::class.java)
            startActivity(intent)
        }
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user !=null){
            if(user.isEmailVerified){
                sharedUpLoad(username.text.toString(),password.text.toString())//Funció que carrega les dades al user de la base de dades a shared preferences i al user del controlador
                sharedDownloadLoad()
                ProgressDialogDisable()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show()
                ProgressDialogDisable()
            }
        }
        ProgressDialogDisable()
    }

    fun sharedUpLoad(email: String, pasword: String, username:String="Joan",gender:String="Masculin",yearBirth:Int=1999, weight:Float=67F,height:Float=180F) {
        val editor = getSharedPreferences("Mydata", Context.MODE_PRIVATE).edit()
        editor.putString("email",email)
        editor.putString("pasword",pasword)
        //Ara hauriem d'anar a la base de dades i agafar la resta, jo ara posaré valors fixes, però el pròxim pas és conectar-ho amb el firebase
        editor.putString("username",username)
        editor.putString("gender",gender)
        editor.putString("yearBirth",yearBirth.toString())
        editor.putString("weight",weight.toString())
        editor.putString("height",height.toString())
        editor.putString("ActivityReminder","")
        editor.putString("MeasurementReminder","")
        editor.putString("MedicationReminder","")
        editor.apply()
    }
    fun sharedDownloadLoad(){
        //Log.d("hola",getSharedPreferences("Mydata", Context.MODE_PRIVATE).contains("email").toString())
        if(getSharedPreferences("Mydata", Context.MODE_PRIVATE).contains("email")){
            //Log.d("hola",getSharedPreferences("Mydata", Context.MODE_PRIVATE).contains("email").toString())
            Toast.makeText(this,"Preparing Data!", Toast.LENGTH_LONG).show()
            //Log.d("hola",getSharedPreferences("Mydata", Context.MODE_PRIVATE).contains("email").toString())

            var prefs = getSharedPreferences("Mydata", Context.MODE_PRIVATE)
            Controller.user.email=prefs.getString("email","")

            Controller.user.pasword=  prefs.getString("pasword","")

            Controller.user.username = prefs.getString("username","")

            Controller.user.gender = prefs.getString("gender","")

            Controller.user.birthYear= prefs.getString("yearBirth","").toInt()

            Controller.user.weight= prefs.getString("weight","").toFloat()

            Controller.user.height= prefs.getString("height","").toFloat()
/*
            //He separat els reminders en 3 perque no sabia com passar de JSON a array de reminders amb diferents constructors
            val gson = Gson()
            var jsonList=prefs.getString("ActivityReminder","")

            var arrayTutorialType = object : TypeToken<Array<ActivityReminder>>() {}.type
            var reminder_list: Array<Reminder> = gson.fromJson(jsonList, arrayTutorialType)
            for(reminder in reminder_list){
                Controller.user.reminders.add(reminder)
            }
            jsonList=prefs.getString("MeasurementReminder","")

            arrayTutorialType = object : TypeToken<Array<MeasurementReminder>>() {}.type
            reminder_list = gson.fromJson(jsonList, arrayTutorialType)
            for(reminder in reminder_list){
                Controller.user.reminders.add(reminder)
            }
            jsonList=prefs.getString("MedicineReminder","")

            arrayTutorialType = object : TypeToken<Array<MedicineReminder>>() {}.type
            reminder_list= gson.fromJson(jsonList, arrayTutorialType)
            for(reminder in reminder_list){
                Controller.user.reminders.add(reminder)
            }
*/
            //Controller.user.reminders=getSharedPreferences("Mydata", Context.MODE_PRIVATE)

            //Log.d("weight",getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("height","7").toString())
            //val intent =Intent(this,MainActivity_sh)
            //tvCognom.setText(last_name)
            //tvNom.setText(name)
            //tvEmail.setText(correu)
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun sharedUpLoad_and_go_back(){
        sharedUpLoad(Controller.user.email,Controller.user.pasword,Controller.user.username,Controller.user.gender,Controller.user.birthYear,Controller.user.weight,Controller.user.height)
        onBackPressed()
        //val intent = Intent(this@LoginActivity, myAccount::class.java)
        //startActivity(intent)
    }
    fun closeSesion(){
        val editor = getSharedPreferences("Mydata", Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }

    fun createAccount(){
        sharedUpLoad(Controller.user.email,Controller.user.pasword,Controller.user.username,Controller.user.gender,Controller.user.birthYear,Controller.user.weight,Controller.user.height)
        Controller.createAccount_in_Firebase()
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    /*fun preferencesSaved(context: Context){
        Controller.initUserSaved()
        val intent = Intent(context , MainActivity::class.java)
        startActivity(intent)
    }

    fun configView(context: Context){
        if(isSavedName()) preferencesSaved(context)
    }

    fun isSavedName():Boolean{
        Log.d("Hola",SharedApp.prefs.username)
        //val username=getSharedPreferences("Pasword",Conte)
        return SharedApp.prefs.username != ""
    }//Aqui retorno true si hi ha algo guardat a shared preferences i false si no hi ha res
*/
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed(): Unit {
    }
    fun ProgressDialogEnable(){
        pDialog.show()
    }
    fun ProgressDialogDisable(){
        if (pDialog.isShowing)
            pDialog.dismiss()
    }
    fun ProgressDialogInit(){
        pDialog = ProgressDialog(this)
        pDialog.setMessage("Please Wait")
        pDialog.setCancelable(false)
    }
}



