package com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import kotlinx.android.synthetic.main.change_pasword_dialogue.view.*
import kotlinx.android.synthetic.main.gender_dialoge.view.*
import kotlinx.android.synthetic.main.height_dialoge.view.*
import kotlinx.android.synthetic.main.height_dialoge.view.cancel
import kotlinx.android.synthetic.main.my_account_activity.*
import kotlinx.android.synthetic.main.weight_dialoge.view.*
import kotlinx.android.synthetic.main.year_of_birth_dialoge.view.*
import kotlinx.android.synthetic.main.year_of_birth_dialoge.view.OK


class myAccount : AppCompatActivity() {
    var username=Controller.user.username
    var email=Controller.user.email
    var gender=Controller.user.gender
    var birth_year=Controller.user.birthYear
    var height=Controller.user.height
    var weight=Controller.user.weight
    var password=Controller.user.pasword
    var new_gender=gender.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_account_activity)
        back_iicon.setOnClickListener {
            onBackPressed()
        }
        Log.e("myAcount", "pwd:  ${Controller.user}")
        val arrayAdapter2: ArrayAdapter<*>

        val users2= arrayOf("", "", username, email, birth_year, gender, height,weight)
        val users3=users2 as Array<Any>
        //getActualMyAccount()
        // access the listView from xml file
        var mListView2 = findViewById<ListView>(R.id.opcions_menuu_actual)
        arrayAdapter2 = ArrayAdapter(this,android.R.layout.simple_list_item_1, users3)
        opcions_menuu_actual.adapter = arrayAdapter2
        val arrayAdapter: ArrayAdapter<*>
        val users = arrayOf("Change password", "Extra information","Username","Email","Year of birth","Gender", "Height","Weight")
        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.opcions_menu)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        opcions_menuu.adapter = arrayAdapter
        opcions_menuu.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> onSelectedMenu(position, users3, arrayAdapter2)
        }


        //val users2= arrayOf("","","",gender,birth_year,height,weight)


        //val users2= arrayOf("", "", "","Username","Email","Year of birth","Gender", "Height","weight")

        //getActualMyAccount()
        // access the listView from xml file
        //var mListView2 = findViewById<ListView>(R.id.opcions_menuu_actual)
    }

    private fun onSelectedMenu(position: Int, users2: Array<Any>, arrayAdapter2:ArrayAdapter<*>){
        if(position==0){//Change Pasword
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.change_pasword_dialogue, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Change password")
            //show dialog
            val  mAlertDialog = mBuilder.show()
            //login button click of custom layout
            mDialogView.OK.setOnClickListener {
                //get text from EditTexts of custom layout
                val old_pasword = mDialogView.input_old_pasword.text.toString() //aquesta variable servirà per actualitzar l'edat
                val new_pasword = mDialogView.input_new_pasword.text.toString()
                val new_repeat_pasword = mDialogView.input_repeat_new_pasword.text.toString()
                if(old_pasword.equals(password)){
                    if(new_pasword.equals(new_repeat_pasword)){
                        if(new_pasword.length<6){
                            //Toast.makeText(this,"Passwords need 7 characters minimum ", Toast.LENGTH_SHORT).show()
                            Toast.makeText(this,"Password should be at least 6 characters",Toast.LENGTH_LONG).show()
                        }
                        else{
                            Controller.updatePassword(new_pasword)
                            mAlertDialog.dismiss()
                        }
                    }
                    else{
                        Toast.makeText(this,"Wrong repeated password",Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Log.e("---------------------", "1 data: ${old_pasword}")
                    Log.e("---------------------", "2 data: $password")
                    Toast.makeText(this,"Wrong old password",Toast.LENGTH_LONG).show()
                    mAlertDialog.dismiss()
                }
            }
            //cancel button click of custom layout
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this,"cancel",Toast.LENGTH_SHORT).show()
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }
        else if(position==1) { // Extra information
        }
        else if(position==5) { //Gender
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.gender_dialoge, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("New gender")
            //show dialog
            val  mAlertDialog = mBuilder.show()

            mDialogView.input_gender.setOnClickListener{
                showPopupMenu(it)
                mDialogView.input_gender.setText(new_gender.toString())
                mDialogView.input_gender.refreshDrawableState()
            }
            //login button click of custom layout
            mDialogView.OK.setOnClickListener {
                //get text from EditTexts of custom layout
                gender=new_gender.toString()
                Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
                //dismiss dialog
                mAlertDialog.dismiss()
                refreshMyAccount(users2,arrayAdapter2)

            }
            //cancel button click of custom layout
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this,"cancel",Toast.LENGTH_SHORT).show()
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }
        else if(position==4){//Year of birth
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.year_of_birth_dialoge, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Year of birth")
            //show dialog
            val  mAlertDialog = mBuilder.show()
            //login button click of custom layout
            mDialogView.OK.setOnClickListener {
                //get text from EditTexts of custom layout
                val new_year_of_birth = mDialogView.input.text.toString() //aquesta variable servirà per actualitzar l'edat
                if(!new_year_of_birth.equals("")){
                    Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
                    birth_year=new_year_of_birth.toInt()
                    //dismiss dialog
                    mAlertDialog.dismiss()
                    refreshMyAccount(users2,arrayAdapter2)
                }
            }
            //cancel button click of custom layout
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this,"cancel",Toast.LENGTH_SHORT).show()
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }
        else if(position==6){//Height
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.height_dialoge, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Height")
            //show dialog
            val  mAlertDialog = mBuilder.show()
            //login button click of custom layout
            mDialogView.OK_height.setOnClickListener {
                val new_height = mDialogView.input_height.text.toString() //aquesta variable servirà per actualitzar l'edat
                if(!new_height.equals("")) {
                    Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
                    height=new_height.toFloat()
                    //dismiss dialog
                    mAlertDialog.dismiss()
                    refreshMyAccount(users2,arrayAdapter2)
                }


            }
            //cancel button click of custom layout
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this,"cancel",Toast.LENGTH_SHORT).show()
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }
        else if(position==7){//weight
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.weight_dialoge, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Weight")
            //show dialog
            val  mAlertDialog = mBuilder.show()
            //login button click of custom layout
            mDialogView.OK_weight.setOnClickListener {
                val new_weight = mDialogView.input_weight.text.toString() //aquesta variable servirà per actualitzar l'edat
                if(!new_weight.equals("")){
                    //dismiss dialog
                    weight=new_weight.toFloat()
                    Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
                    mAlertDialog.dismiss()
                    refreshMyAccount(users2,arrayAdapter2)
                }
            }
            //cancel button click of custom layout
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this,"cancel",Toast.LENGTH_SHORT).show()
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }
    }

    private fun refreshMyAccount(users2: Array<Any>, arrayAdapter2:ArrayAdapter<*>){
        users2.set(5,gender)
        users2.set(4,birth_year)
        users2.set(6,height)
        users2.set(7,weight)
        arrayAdapter2.notifyDataSetChanged()
        Controller.refreshMyAccount(email,username,gender,birth_year,height,weight)
        //go_back()
        /*val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("type_of_action","Save_Share_and_go_back")
        startActivity(intent)*/
    }
    fun go_back(){
        onBackPressed()
    }
    private fun showPopupMenu(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.gender_popup_menu, menu)
        setOnMenuItemClickListener { item ->
            Toast.makeText(view.context, "You Clicked : ${item.title}", Toast.LENGTH_SHORT).show()
            new_gender=item.title.toString()
            view.input_gender.setText(new_gender.toString())
            true
        }
        show()
    }
}
