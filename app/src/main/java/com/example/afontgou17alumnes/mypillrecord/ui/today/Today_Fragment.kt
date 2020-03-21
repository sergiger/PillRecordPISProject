package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.BasicMedicineReminder
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.MedicineListAdapter
import com.example.afontgou17alumnes.mypillrecord.ui.register.activity_Register4
import com.example.afontgou17alumnes.mypillrecord.ui.login.LoginActivity
import kotlinx.android.synthetic.main.today__fragment.*
import java.time.LocalTime

/**
 * A simple [Fragment] subclass.
 */
class Today_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.today__fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createTodayList()
    }

    fun createTodayList(){
        val medicineList = arrayOf(
            BasicMedicineReminder("Ibuprofeno", 1, LocalTime.of(16,4,0)),
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
        val medicineListView : ListView? = view?.findViewById(R.id.today_list)
        val medicineAdapter : MedicineListAdapter = MedicineListAdapter(this,medicineList)
        if (medicineListView != null) {
            medicineListView.adapter = medicineAdapter
        }
    }
}
