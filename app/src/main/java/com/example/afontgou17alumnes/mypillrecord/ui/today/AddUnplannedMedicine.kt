package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.NumberPicker
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_add_unplanned_activity.back_arrow
import kotlinx.android.synthetic.main.activity_add_unplanned_medicine.*
import kotlinx.android.synthetic.main.number_dialog.*
import kotlinx.android.synthetic.main.number_dialog.view.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.OK
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.cancel
import java.text.NumberFormat

class AddUnplannedMedicine : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_unplanned_medicine)

        //Set Listeners
        back_arrow.setOnClickListener{
            onBackPressed()
        }
        tick_unplanned_medicine.setOnClickListener{
            val tickIntent = Intent(this, MainActivity::class.java)
            startActivity(tickIntent)
        }
        date_button.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.date_dialoge, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Set date")
            val mAlertDialog = mBuilder.show()
            mDialogView.OK.setOnClickListener {
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
        }
        hour_button_unplanned_medicine.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.time_dialog, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Set date")
            val mAlertDialog = mBuilder.show()
            mDialogView.OK.setOnClickListener {
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
        }
        dose_button.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.number_dialog, null)
            //Set Number Picker
            mDialogView.number_Picker.minValue = 1
            mDialogView.number_Picker.maxValue = 100
            mDialogView.number_Picker.wrapSelectorWheel = false

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Set dose")
            val mAlertDialog = mBuilder.show()
            mDialogView.OK.setOnClickListener {
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
        }
        medicine_name_button.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.search_dialog, null)

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Set medicine")
            val mAlertDialog = mBuilder.show()
            mDialogView.OK.setOnClickListener {
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
            mDialogView.cancel.setOnClickListener {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                mAlertDialog.dismiss()
            }
        }
    }

}
