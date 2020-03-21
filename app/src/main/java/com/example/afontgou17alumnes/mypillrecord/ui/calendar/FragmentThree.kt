package com.example.afontgou17alumnes.mypillrecord.ui.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.BasicMedicineReminder
import java.sql.Time
import java.time.LocalTime

/**
 * A simple [Fragment] subclass.
 */
class FragmentThree : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createMedicineList()
    }

    fun createMedicineList(){
        val medicineList = arrayOf(BasicMedicineReminder("Ibuprofeno", 1, LocalTime.of(16,4,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0)),
            BasicMedicineReminder("Paracetamol", 2, LocalTime.of(19,30,0))
        )
        val medicineListView : ListView? = view?.findViewById(R.id.medicine_list)
        val medicineAdapter : MedicineListAdapter= MedicineListAdapter(this,medicineList)
        if (medicineListView != null) {
            medicineListView.adapter = medicineAdapter
        }
    }


}
