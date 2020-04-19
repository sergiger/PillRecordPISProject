package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_add_unplanned_entry.*
import kotlin.math.roundToInt

class AddUnplannedEntry : DialogFragment() {

    companion object {
        fun newInstance() =
            AddUnplannedEntry()
    }

    override fun onResume() {
        super.onResume()
        val dm = DisplayMetrics()
        dialog.window!!.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels*0.85
        val height = dm.heightPixels*0.4
        dialog.window!!.setLayout(width.roundToInt(), height.roundToInt())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_add_unplanned_entry, null)
        //View elements
        val medicineBtn = view.findViewById<Button>(R.id.medicine_entry_button)
        val measurementBtn = view.findViewById<Button>(R.id.measurement_entry_button)
        val activityBtn = view.findViewById<Button>(R.id.activity_entry_button)

        //Set intents
        medicineBtn.setOnClickListener{
            val medicineIntent = Intent(context, AddUnplannedMedicine::class.java)
            startActivity(medicineIntent)
        }
        measurementBtn.setOnClickListener{
            val measurementIntent = Intent(context, AddUnplannedMeasurement::class.java)
            startActivity(measurementIntent)
        }
        activityBtn.setOnClickListener{
            val activityIntent = Intent(context, AddUnplannedActivity::class.java)
            startActivity(activityIntent)
        }

        return view
    }

}
