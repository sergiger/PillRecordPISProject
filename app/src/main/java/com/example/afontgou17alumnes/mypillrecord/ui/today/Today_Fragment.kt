package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.*
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.ReminderListAdapter
import com.example.afontgou17alumnes.mypillrecord.ui.statistics.AddMeasurementDialog
import kotlinx.android.synthetic.main.today__fragment.*
import java.time.LocalDate
import java.time.LocalTime
import kotlin.reflect.typeOf

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
            val mDialog = AddUnplannedEntry()
            mDialog.show(childFragmentManager, "Add unplanned entry")
        }
        today_list.setOnItemClickListener { adapterView, view, i, l ->
            val reminder : Reminder = adapterView.adapter.getItem(i) as Reminder
            val intent = Intent(context, TodayModifyReminder::class.java)
            intent.putExtra("Reminder", reminder)
            startActivity(intent)
        }
    }

    fun createTodayList(){
        val medicineList = arrayOf(
            MedicineReminder("Ibuprofen",3,"tablet(s)", LocalDate.now(), LocalTime.of(17,0)),
            MeasurementReminder("Weight","kg",LocalDate.now(), LocalTime.of(17,0)),
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
        val reminderAdapter = ReminderListAdapter(this,medicineList)
        if (medicineListView != null) {
            medicineListView.adapter = reminderAdapter
        }
    }
}
