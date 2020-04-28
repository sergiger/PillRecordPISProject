package com.example.afontgou17alumnes.mypillrecord.ui.login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.controller.ControllerSharePrefs
import com.example.afontgou17alumnes.mypillrecord.ui.register.activity_Register4
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    lateinit var pDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProgressDialogInit()
        mAuth=FirebaseAuth.getInstance()
        setContentView(R.layout.activity_login)
        ControllerSharePrefs.setContext(this)
        /*
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
        //Controller.controllerSharePrefs.closeSesion()
        Controller.controllerSharePrefs.sharedDownloadLoad()
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user !=null){
            if(user.isEmailVerified){
                //sharedUpLoad(username.text.toString(),password.text.toString())//Funció que carrega les dades al user de la base de dades a shared preferences i al user del controlador
                //sharedDownloadLoad()
                getdatafromfirebase()
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

    private fun getdatafromfirebase() {
        val db = FirebaseFirestore.getInstance()
        val user: FirebaseUser? = mAuth.getCurrentUser()
        val id =user?.uid
        //dàdes d'usuari
        val docRef = id?.let { db.collection("users").document(id) }
        docRef?.get()?.addOnSuccessListener { document ->
                if (document != null) {
                    Log.e("getDatafromfirestore", "DocumentSnapshot data: ${document.data}")
                    var map= document.data as MutableMap<String, Any?>
                    var y =map["yearBirth"] as Number
                    val h = map["height"] as Number
                    val w = map["weight"] as Number
                    Controller.downloadUserAccount(id,map["email"] as String,map["username"]as String,map["gender"] as String,y.toInt(),h.toFloat(),w.toFloat(),map["password"] as String)
                    Log.e("getDatafromfirestore", "get failed with ${map["password"]}")
                } else {
                    Log.e("getDatafromfirestore", "No such document")
                }
            }?.addOnFailureListener { exception ->
                Log.e("getDatafromfirestore", "get failed with ", exception)
            }

    }
    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
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



