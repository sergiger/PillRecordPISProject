package com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.*

import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.ui.login.LoginActivity
import kotlinx.android.synthetic.main.my_account_activity.*
import kotlinx.android.synthetic.main.change_pasword_dialogue.view.*
import kotlinx.android.synthetic.main.gender_dialoge.*
import kotlinx.android.synthetic.main.gender_dialoge.view.*
import kotlinx.android.synthetic.main.height_dialoge.view.*
import kotlinx.android.synthetic.main.height_dialoge.view.cancel
import kotlinx.android.synthetic.main.weight_dialoge.view.*
import kotlinx.android.synthetic.main.year_of_birth_dialoge.view.*
import kotlinx.android.synthetic.main.year_of_birth_dialoge.view.OK


class myAccount : AppCompatActivity() {
    var gender="Masculin"
    var birth_year=1999
    var height=181
    var weight=67

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_account_activity)


        back_iicon.setOnClickListener {
            onBackPressed()
        }
        val arrayAdapter2: ArrayAdapter<*>

        val users2= arrayOf("","","",gender,birth_year,height,weight)

        //getActualMyAccount()
        // access the listView from xml file
        var mListView2 = findViewById<ListView>(R.id.opcions_menuu_actual)
        arrayAdapter2 = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, users2)
        opcions_menuu_actual.adapter = arrayAdapter2


        val arrayAdapter: ArrayAdapter<*>
        val users = arrayOf("Change Pasword", "Close Session",
            "Extra information","Gender","Year of birth", "Height","weight")
        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.opcions_menu)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, users)
        opcions_menuu.adapter = arrayAdapter
        opcions_menuu.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            onSelectedMenu(position,users2, arrayAdapter2)
        }






    }
    private fun onSelectedMenu(position: Int, users2: kotlin.Array<Any>, arrayAdapter2:ArrayAdapter<*>){
        if(position==0){//Change Pasword
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.change_pasword_dialogue, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Change pasword")
            //show dialog
            val  mAlertDialog = mBuilder.show()
            //login button click of custom layout
            mDialogView.OK.setOnClickListener {
                //get text from EditTexts of custom layout
                val old_pasword = mDialogView.input_old_pasword.text.toString() //aquesta variable servirà per actualitzar l'edat
                val new_pasword = mDialogView.input_new_pasword.text.toString()
                val new_repeat_pasword = mDialogView.input_repeat_new_pasword.text.toString()
                if(new_pasword==new_repeat_pasword){
                    if(!new_pasword.equals("")) {
                        //dismiss dialog
                        mAlertDialog.dismiss()
                    }
                }else{
                    Toast.makeText(this,"Wrong repeated pasword",Toast.LENGTH_LONG).show()
                }
            }
            //cancel button click of custom layout
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this,"cancel",Toast.LENGTH_SHORT).show()
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }
        else if(position==1){//Close Session
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent);
            Toast.makeText(this,"sesion closed", Toast.LENGTH_SHORT).show()
        }
        else if(position==2){//Extra information
            val intent = Intent(this, extra_information::class.java);
            startActivity(intent);
            //Aqui s'haurà de posar que el user i la contrassenya son null, per a que no puguis tornar
            //a entrar a la conta amb el mateix user
        }
        else if(position==3){//Gender
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
                mDialogView.input_gender.setText(gender.toString())
                mDialogView.input_gender.refreshDrawableState()
            }
            //login button click of custom layout
            mDialogView.OK.setOnClickListener {
                //get text from EditTexts of custom layout
                val new_gender = mDialogView.input_gender.text.toString() //aquesta variable servirà per actualitzar l'edat
                if(!new_gender.equals("")){
                    Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
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
        else if(position==5){//Height
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
                    height=new_height.toInt()
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
        else if(position==6){//weight
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
                    weight=new_weight.toInt()
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

        users2.set(3,gender)
        users2.set(4,birth_year)
        users2.set(5,height)
        users2.set(6,weight)
        arrayAdapter2.notifyDataSetChanged()
    }

    fun showPopupMenu(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.gender_popup_menu, menu)
        setOnMenuItemClickListener { item ->
            Toast.makeText(view.context, "You Clicked : ${item.title}", Toast.LENGTH_SHORT).show()
            gender=item.title.toString()
            true
        }
        show()
    }
}
