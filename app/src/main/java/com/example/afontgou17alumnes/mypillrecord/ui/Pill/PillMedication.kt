package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_pill_medication.*
import kotlinx.android.synthetic.main.activity_pill_sports.*
import kotlinx.android.synthetic.main.activity_pill_sports.btn_Save
import kotlinx.android.synthetic.main.activity_pill_sports.btn_frequency
import kotlinx.android.synthetic.main.activity_pill_sports.btn_from
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*

class PillMedication : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_medication)
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
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                //TODO AGAFAR DÂDES DELS DIES
                mAlertDialog.dismiss()
            }
            mDialogView.cancel.setOnClickListener {
                mAlertDialog.dismiss()
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }
        }
        btn_frequency.setOnClickListener {
            val intent = Intent(this, PillFrequency::class.java)
            startActivity(intent)
        }
        btn_Save.setOnClickListener {
            Toast.makeText(this, "New plan added", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btn_scan.setOnClickListener {
            Toast.makeText(this, "TODO", Toast.LENGTH_LONG).show()
        }
    }
}
