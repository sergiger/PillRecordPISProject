package com.example.afontgou17alumnes.mypillrecord.ui.calendar

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.UnplannedMedicineReminder
import java.time.LocalTime

/**
 * A simple [Fragment] subclass.
 */
class FragmentOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createWeekList()
    }

    fun createWeekList(){
        val medicineList = arrayOf(
            UnplannedMedicineReminder("Ibuprofen", 1, "tablet(s)", LocalTime.of(16,4,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0)),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)", LocalTime.of(19,30,0))
        )
        val medicineListView : ListView? = view?.findViewById(R.id.week_list)
        val reminderAdapter : ReminderListAdapter= ReminderListAdapter(this,medicineList)
        if (medicineListView != null) {
            medicineListView.adapter = reminderAdapter
        }
    }
}
