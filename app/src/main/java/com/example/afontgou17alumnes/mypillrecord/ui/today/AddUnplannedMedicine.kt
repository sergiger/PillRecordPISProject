package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_add_unplanned_activity.back_arrow
import kotlinx.android.synthetic.main.activity_add_unplanned_medicine.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*

class AddUnplannedMedicine : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_unplanned_medicine)
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
    }

}
