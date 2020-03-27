package com.example.afontgou17alumnes.mypillrecord.ui.statistics

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.add_measure_dialog.*

class AddMeasurementDialog : DialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_measure_dialog, null)
        val ok = view.findViewById<Button>(R.id.OK_add_measure_dialog)
        dialog.setTitle("Add measurement")
        ok?.setOnClickListener {
            dismiss()
        }

        return view
    }
}