package com.example.afontgou17alumnes.mypillrecord.ui.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.User
import com.example.afontgou17alumnes.mypillrecord.ui.login.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity__register4.*
import kotlinx.android.synthetic.main.activity__register4.view.*


class activity_Register4 : AppCompatActivity() {
    var email: String = ""
    var username: String=""
    var password: String=""
    var password_repeat: String=""
    var year_Birth= 0
    var gender: String=""
    var weight: Float= 0F
    var height:Float= 0F
    private lateinit var mAuth: FirebaseAuth
    lateinit var pDialog: ProgressDialog

    private lateinit var database: DatabaseReference// ...


    override fun onCreate(savedInstanceState: Bundle?) {
        ProgressDialogInit()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__register4)
        mAuth=FirebaseAuth.getInstance()
        //Això és per a veure el flow, no és per a fer que funcionin els botons de veritat
        text_input_gender.setOnClickListener {
            showPopupMenu(it)
            text_input_gender.setText(gender.toString())
        }


        btn_confirm_register.setOnClickListener {
            /*
            * Aqui només has de fer que s'omplin totes les variables.
            * També has d'enviar errors quan l'email ja estigui a la base de dades, és a dir, només una conta per email
            *
            * */
            createAccount()
        }
    }

    fun showPopupMenu(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.gender_popup_menu, menu)
        setOnMenuItemClickListener { item ->
            //Toast.makeText(view.context, "You Clicked : ${item.title}", Toast.LENGTH_SHORT).show()
            gender=item.title.toString()
            view.text_input_gender.setText(gender.toString())
            text_input_gender.error= null
            true
        }
        show()
    }

    fun createAccount(){
        ProgressDialogEnable()
        val ok = errorsManage()
        if(ok){
            this.year_Birth=text_input_age.text.toString().toInt()
            this.weight=text_input_weight.text.toString().toFloat()
            this.height=text_input_height.text.toString().toFloat()
            if (password.equals(password_repeat)){
                mAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this,
                        OnCompleteListener<AuthResult?> { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val intent = Intent(this, LoginActivity::class.java)
                                intent.putExtra("type_of_action","Save_share_Create_Account_Go_Home")
                                val user: FirebaseUser? = mAuth.getCurrentUser()
                                user?.sendEmailVerification()
                                Controller.createAccount( user!!.uid,this.email,this.username,this.password,this.gender,this.year_Birth,this.weight,this.height)
                                createuserfirebase(user!!.uid,this.email,this.username,this.password,this.gender,this.year_Birth,this.weight,this.height)
                                startActivity(intent)

                            } else {
                                // If sign in fails, display a message to the user. errors from firebase
                                ProgressDialogDisable()
                                try {
                                    throw task.exception!!
                                }  catch (e: FirebaseAuthUserCollisionException) {
                                    text_input_email.setError(e.message)
                                    text_input_email.requestFocus()
                                } catch (e: FirebaseAuthInvalidCredentialsException) {
                                    text_input_email.setError(e.message)
                                    text_input_email.requestFocus()
                                }catch (e: Exception) {
                                    Log.e("register fail :", e.message)
                                }
                                Log.w("Sign in","signInWithEmail:failure",task.exception)
                                Toast.makeText(this,"Authentication failed.",Toast.LENGTH_SHORT).show()

                            }

                            // ...
                        })
            }
            else{
                ProgressDialogDisable()
                Toast.makeText(this,"passwords do not match", Toast.LENGTH_SHORT).show()
                val text1: Editable = SpannableStringBuilder("")
                text_input_repeat_password.text=text1
            }
        }
        ProgressDialogDisable()
    }

     fun createuserfirebase(
        uid: String,
        email: String,
        username: String,
        password: String,
        gender: String,
        yearBirth: Int,
        weight: Float,
        height :Float
    ) {
         /*database = FirebaseDatabase.getInstance().reference
        val user2=User(uid,email,username,password,gender,yearBirth,weight,height)*/
         val db = FirebaseFirestore.getInstance()
         val user = hashMapOf(
             "uid" to uid ,
             "email" to email,
             "username" to username,
             "password" to password,
             "gender" to gender,
             "yearBirth" to yearBirth,
             "weight" to weight,
             "height" to height
         )
        // Add a new document with a specific id
         db.collection("users").document(uid)
             .set(user)
             .addOnSuccessListener { documentReference ->
                 Log.d("TAG", "DocumentSnapshot added with ID: ${uid}")
             }
             .addOnFailureListener { e ->
                 Log.w("TAG", "Error adding document", e)
             }
    }

    private fun errorsManage(): Boolean {
        //local errors
        var cont =0

        this.email=text_input_email.text.toString().trim()
        this.username=text_input_username.text.toString().trim()
        this.password=text_input_password.text.toString().trim()
        this.password_repeat=text_input_repeat_password.text.toString().trim()
        this.gender=text_input_gender.text.toString().trim()
        val year_Birth1=text_input_age.text.toString()
        val weight1=text_input_weight.text.toString()
        val height1=text_input_height.text.toString()

        if(email.isEmpty()){
            text_input_email.setError("Email is Required")
            cont++
        }
        if(username.isEmpty()){
            text_input_username.setError("Username is Required")
            cont++

        }
        if(password.isEmpty()){
            text_input_password.setError("Pasword is Required")
            cont++

        }
        if(password_repeat.isEmpty()){
            text_input_repeat_password.setError("Pasword is Required")
            cont++

        }
        if(gender.isEmpty()){
            text_input_gender.setError("Gender is Required")
            cont++
        }
        if(year_Birth1.isEmpty()){
            text_input_age.setError("Year of birth is Required")
            cont++

        }
        if(weight1.isEmpty()){
            text_input_weight.setError("Weight is Required")
            cont++

        }
        if(height1.isEmpty()){
            text_input_height.setError("Height is Required")
            cont++

        }

        if(password.length<6){
            //Toast.makeText(this,"Passwords need 7 characters minimum ", Toast.LENGTH_SHORT).show()
            text_input_password.setError("Password should be at least 6 characters")
            val text1: Editable = SpannableStringBuilder("")
            text_input_repeat_password.text=text1
            text_input_password.text=text1
            cont++
        }

        if(cont != 0)
            return false

        return true
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
