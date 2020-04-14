package com.example.afontgou17alumnes.mypillrecord.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.UnplannedMedicineReminder
import java.time.LocalDate
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
            MedicineReminder("Ibuprofen",3,"tablet(s)", LocalDate.now(), LocalTime.of(17,0)),
            MeasurementReminder("Weight","kg", LocalDate.now(), LocalTime.of(17,0)),
            ActivityReminder("Running", 15, LocalDate.now(), LocalTime.of(18,0)),
            UnplannedMedicineReminder("Ibuprofen", 1, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)" ),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)")
        )
        val medicineListView : ListView? = view?.findViewById(R.id.week_list)
        val reminderAdapter : WeekListAdapter = WeekListAdapter (this, medicineList)
        if (medicineListView != null) {
            medicineListView.adapter = reminderAdapter
        }
    }
}
