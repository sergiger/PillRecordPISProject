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
import com.example.afontgou17alumnes.mypillrecord.data.SharedApp
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.ui.register.activity_Register4
import com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation.myAccount
import kotlinx.android.synthetic.main.activity_login.*


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

            }
            btn_register.setOnClickListener {

                val intent = Intent(this@LoginActivity, activity_Register4::class.java)
                startActivity(intent)
            }
        }
    }

    fun sharedUpLoad(email: String, pasword: String) {
        val editor = getSharedPreferences("Mydata", Context.MODE_PRIVATE).edit()
        editor.putString("email",email);
        editor.putString("pasword",pasword);
        //Ara hauriem d'anar a la base de dades i agafar la resta, jo ara posaré valors fixes, però el pròxim pas és conectar-ho amb el firebase
        editor.putString("username","Joan")
        editor.putString("gender","Masculin");
        editor.putString("yearBirth","1999");
        editor.putString("weight","67");
        editor.putString("height","180");
        editor.apply();
        sharedDownloadLoad()//per posar-ho tot al controlador
    }
    fun sharedDownloadLoad(){
        //Log.d("hola",getSharedPreferences("Mydata", Context.MODE_PRIVATE).contains("email").toString())
        if(getSharedPreferences("Mydata", Context.MODE_PRIVATE).contains("email")){
            //Log.d("hola",getSharedPreferences("Mydata", Context.MODE_PRIVATE).contains("email").toString())
            Toast.makeText(this,"User found!", Toast.LENGTH_SHORT).show()
            //Log.d("hola",getSharedPreferences("Mydata", Context.MODE_PRIVATE).contains("email").toString())
            Controller.user.email=getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("email","")
            Log.d("email",getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("email","1").toString())

            Controller.user.pasword=  getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("pasword","")
            Log.d("pasword",getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("pasword","2").toString())

            Controller.user.username = getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("username","")
            Log.d("username",getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("username","3").toString())

            Controller.user.gender = getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("gender","")
            Log.d("gender",getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("gender","4").toString())

            Controller.user.birthYear= getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("yearBirth","").toInt()
            Log.d("birth",getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("yearBirth","5").toString())

            Controller.user.weight= getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("weight","").toFloat()
            Log.d("weight",getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("weight","6").toString())

            Controller.user.height=  getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("height","").toFloat()
            Log.d("weight",getSharedPreferences("Mydata", Context.MODE_PRIVATE).getString("height","7").toString())
            //val intent =Intent(this,MainActivity_sh)
            //tvCognom.setText(last_name)
            //tvNom.setText(name)
            //tvEmail.setText(correu)
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun sharedUpLoad_and_go_back(){
        val editor = getSharedPreferences("Mydata", Context.MODE_PRIVATE).edit()
        editor.putString("email",Controller.user.email);
        editor.putString("pasword",Controller.user.pasword);
        //Ara hauriem d'anar a la base de dades i agafar la resta, jo ara posaré valors fixes, però el pròxim pas és conectar-ho amb el firebase
        editor.putString("username",Controller.user.username)
        editor.putString("gender",Controller.user.gender);
        editor.putString("yearBirth",Controller.user.birthYear.toString());
        editor.putString("weight",Controller.user.weight.toString());
        editor.putString("height",Controller.user.height.toString());
        editor.apply();
        onBackPressed()
        //val intent = Intent(this@LoginActivity, myAccount::class.java)
        //startActivity(intent)
    }
    fun closeSesion(){
        val editor = getSharedPreferences("Mydata", Context.MODE_PRIVATE).edit()
        editor.clear();
        editor.apply();
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



