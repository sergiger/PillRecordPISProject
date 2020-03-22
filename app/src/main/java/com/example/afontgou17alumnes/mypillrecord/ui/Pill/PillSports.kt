package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_pill_sports.*
import kotlinx.android.synthetic.main.share_team_activity.*

class PillSports : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_sports)
        val image_view = findViewById(R.id.left_arrow) as ImageButton
        image_view.setOnClickListener {
            onBackPressed()
        }
        btn_from.setOnClickListener {
            //dialoge_date()
            val mDialogView =
                LayoutInflater.from(this).inflate(R.layout.specific_dates_dialoge, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Set specific dates")
            //show dialog
            val mAlertDialog = mBuilder.show()
            //mDialogView.OK.setOnClickListener {
                /*val datePicker_ini = findViewById<DatePicker>(R.id.date_Picker_ini)
                val today = Calendar.getInstance()
                datePicker_ini.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH)

                ) { view, year, month, day ->
                    val month = month + 1
                    val msg = "You Selected: $day/$month/$year"
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                }*/
                //val intent = Intent(this,PillAddDay::class.java)
                //startActivity(intent)
            //}
            }
            btn_frequency.setOnClickListener {
                val intent = Intent(this, PillFrequency::class.java)
                startActivity(intent)
            }
        }
    }


