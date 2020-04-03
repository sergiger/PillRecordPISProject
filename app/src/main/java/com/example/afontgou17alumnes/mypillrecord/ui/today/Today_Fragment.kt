package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.content.ContentProvider
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.UnplannedMedicineReminder
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.ReminderListAdapter
import kotlinx.android.synthetic.main.today__fragment.add_button
import java.time.LocalDate
import java.time.LocalTime

/**
 * A simple [Fragment] subclass.
 */
class Today_Fragment : Fragment() {

    companion object {
        fun newInstance() =
            Today_Fragment()
    }

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
        add_button.setOnClickListener {
            val intent = Intent(activity, AddUnplannedEntry::class.java)
            startActivity(intent)
        }
    }

    fun createTodayList(){
        val medicineList = arrayOf(
            MedicineReminder("Ibuprofen",3,"tablet(s)", LocalDate.now(), LocalTime.of(17,0)),
            MeasurementReminder("Weight","kg", Controller.user.weight,LocalDate.now(), LocalTime.of(17,0)),
            ActivityReminder("Running", 15, LocalDate.now(), LocalTime.of(18,0)),
            UnplannedMedicineReminder("Ibuprofen", 1, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)"),
            UnplannedMedicineReminder("Paracetamol", 2, "tablet(s)")
        )
        val medicineListView : ListView? = view?.findViewById(R.id.today_list)
        val reminderAdapter : ReminderListAdapter = ReminderListAdapter(this,medicineList)
        if (medicineListView != null) {
            medicineListView.adapter = reminderAdapter
        }
    }
}
