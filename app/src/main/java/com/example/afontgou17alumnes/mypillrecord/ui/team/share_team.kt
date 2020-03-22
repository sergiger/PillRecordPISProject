package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.share_team_activity.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import kotlinx.android.synthetic.main.weight_dialoge.view.*
import kotlinx.android.synthetic.main.weight_dialoge.view.cancel
import java.util.*

class share_team : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_team_activity)

        val image_view = findViewById(R.id.left_arrow) as ImageButton
        image_view.setOnClickListener {
            onBackPressed()
        }

        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext," On checked change : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
            })

        btn_share.setOnClickListener{
            // Get the checked radio button id from radio group
            var id: Int = radio_group.checkedRadioButtonId
            if (id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using id
                val radio:RadioButton = findViewById(id)
                Toast.makeText(applicationContext,"On button click : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
                //En cas que haguem d'escollir dates concretes, treuré un dialogue
                if(radio.text.equals("Specific dates")){
                    //dialoge_date()
                    val mDialogView = LayoutInflater.from(this).inflate(R.layout.specific_dates_dialoge, null)
                    //AlertDialogBuilder
                    val mBuilder = AlertDialog.Builder(this)
                        .setView(mDialogView)
                        .setTitle("Set specific dates")
                    //show dialog
                    val  mAlertDialog = mBuilder.show()
                    mDialogView.OK.setOnClickListener {
                        /*val datePicker_ini = findViewById<DatePicker>(R.id.date_Picker_ini)
                        val today = Calendar.getInstance()
                        datePicker_ini.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                            today.get(Calendar.DAY_OF_MONTH)

                        ) { view, year, month, day ->
                            val month = month + 1
                            val msg = "You Selected: $day/$month/$year"
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                        }*/
                        Toast.makeText(this,"work in progress",Toast.LENGTH_LONG)
                        val intent = Intent(this, MainActivity::class.java);
                        startActivity(intent);
                    }
                    mDialogView.cancel.setOnClickListener {
                        mAlertDialog.dismiss()
                        Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    val intent = Intent(this, MainActivity::class.java);
                    startActivity(intent);
                    Toast.makeText(this,"Shared succesfully",Toast.LENGTH_SHORT).show()
                }
            }else{
                // If no radio button checked in this radio group
                Toast.makeText(applicationContext,"On button click : nothing selected",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun radio_button_click(view: View){
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(radio_group.checkedRadioButtonId)
        Toast.makeText(applicationContext,"On click : ${radio.text}",
            Toast.LENGTH_SHORT).show()
    }

    fun dialoge_date(){
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.specific_dates_dialoge, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Set specific dates")
        //show dialog
        val  mAlertDialog = mBuilder.show()

        //login button click of custom layout
        /*mDialogView.OK.setOnClickListener {
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
        }*/
    }
}
