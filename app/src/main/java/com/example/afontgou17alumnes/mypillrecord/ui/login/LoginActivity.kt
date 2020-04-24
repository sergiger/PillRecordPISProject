package com.example.afontgou17alumnes.mypillrecord.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.Reminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeMeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeMedicationReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.fakeReminders.FakeReminder
import com.example.afontgou17alumnes.mypillrecord.ui.register.activity_Register4
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.reflect.Type


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val bundle:Bundle? = intent.extras
        val actions = bundle?.get("type_of_action")//Això ens permet accedir al shared preferences, potser és una manera molt cutre, però és la única que consegueixo que funcioni
        if(actions!=null && actions=="Save_Share_and_go_back")
            sharedUpLoad_and_go_back()
        else if(actions!=null && actions=="close_sesion")
            closeSesion()
        else if(actions!=null && actions=="Save_share_Create_Account_Go_Home")
            createAccount()
        else if(actions!=null && actions=="Save_and_go_home")
            sharedUpLoad_and_go_home()
        else
            sharedDownloadLoad()

        //configView(this@LoginActivity)


        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
                sharedUpLoad(username.text.toString(),password.text.toString())//Funció que carrega les dades al user de la base de dades a shared preferences i al user del controlador
                sharedDownloadLoad()
            }
            btn_register.setOnClickListener {

                val intent = Intent(this@LoginActivity, activity_Register4::class.java)
                startActivity(intent)
            }
        }
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
        editor.putString("ActivityReminder",Gson().toJson(Controller.user.getFakeActivityReminders()))
        editor.putString("MeasurementReminder",Gson().toJson(Controller.user.getFakeMeasurementReminders()))
        editor.putString("MedicationReminder",Gson().toJson(Controller.user.getFakeMedicationReminders()))
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

            //He separat els reminders en 3 perque no sabia com passar de JSON a array de reminders amb diferents constructors
            val gson = Gson()
            var tipusArray: Type
            var jsonList=prefs.getString("ActivityReminder","")
            //Log.d("ActivityReminder",jsonList)
            if(jsonList!=""){
                var tipusArray = object : TypeToken<Array<FakeActivityReminder>>() {}.type
                var reminder_list1: Array<FakeReminder> = gson.fromJson(jsonList, tipusArray)
                for(reminder in reminder_list1){
                    Controller.user.reminders.add(reminder.createRealReminder())
                }
            }

            jsonList=prefs.getString("MeasurementReminder","")
            //Log.d("MeasurementReminder",jsonList)
            if(jsonList!=""){
                tipusArray = object : TypeToken<Array<FakeMeasurementReminder>>() {}.type
                var reminder_list2 : Array<FakeReminder> = gson.fromJson(jsonList, tipusArray)
                for(reminder in reminder_list2){
                    Controller.user.reminders.add(reminder.createRealReminder())
                }
            }

            jsonList=prefs.getString("MedicineReminder","")
            //Log.d("MedicationReminder",jsonList)
            if(jsonList!=""){
                tipusArray = object : TypeToken<Array<FakeMedicationReminder>>() {}.type
                var reminder_list3:Array<FakeReminder> = gson.fromJson(jsonList, tipusArray)
                for(reminder in reminder_list3){
                    Controller.user.reminders.add(reminder.createRealReminder())
                }
            }
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun sharedUpLoad_and_go_home(){
        //Log.d("hola","perque o funciona aixó?")
        sharedUpLoad(Controller.user.email,Controller.user.pasword,Controller.user.username,Controller.user.gender,Controller.user.birthYear,Controller.user.weight,Controller.user.height)
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }
    fun sharedUpLoad_and_go_back(){
        sharedUpLoad(Controller.user.email,Controller.user.pasword,Controller.user.username,Controller.user.gender,Controller.user.birthYear,Controller.user.weight,Controller.user.height)
        onBackPressed()
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


    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}



