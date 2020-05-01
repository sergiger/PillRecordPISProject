package com.example.afontgou17alumnes.mypillrecord.ui.calendar

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.UnplannedMedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MedicineReminder
import kotlinx.android.synthetic.main.fragment_two.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentTwo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createCalendarDialog()
        createMonthList()
    }

    fun createCalendarDialog() {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year= c.get(Calendar.YEAR)

        btn_selectDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener {
                    view, myear, monthOfYear, dayOfMonth ->
                    txt_datePicked.text = "The date picked is: " + myear + "/" + monthOfYear + "/" + dayOfMonth
            }, year, month, day)
            datePickerDialog.show()
        }
    }

    fun createMonthList() {
        val medicineList = arrayListOf(
            MedicineReminder(
                "Ibuprofen",
                3,
                "tablet(s)",
                LocalDate.now(),
                LocalTime.of(17, 0)
            ),
            MeasurementReminder(
                "Weight",
                "kg",
                LocalDate.now(),
                LocalTime.of(17, 0)
            ),
            ActivityReminder(
                "Running",
                15,
                LocalDate.now(),
                LocalTime.of(18, 0)
            ),
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

        val medicineListView : ListView? = view?.findViewById(R.id.month_list)
        val medicineAdapter = ReminderListAdapter(this, medicineList)
        if (medicineListView != null) {
            medicineListView.adapter = medicineAdapter
        }

    }
}
