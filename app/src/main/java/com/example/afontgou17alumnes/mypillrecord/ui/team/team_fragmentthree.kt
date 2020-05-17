package com.example.afontgou17alumnes.mypillrecord.ui.team


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.Reminder
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.List.CustomAdapter
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.List.HistoricModify
import kotlinx.android.synthetic.main.fragment_three.*

/**
 * A simple [Fragment] subclass.
 */
class team_fragmentthree : Fragment() {
    lateinit var actualReminder : Reminder
    companion object {
        fun newInstance() = team_fragmentthree()
    }
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
        // Para modificar (Aqui)
    }

    fun createMedicineList() {
        // val medicineList = Controller.getRemindersData()
        val medicineList = Controller.team_calendar_getHistoricDatesAndReminders() // New
        val medicineListView : ListView? = view?.findViewById(R.id.historic_list)
        // New
        val reminderAdapter : CustomAdapter = CustomAdapter(this.context, medicineList) // Controller.getRemindersData()
        if (medicineListView != null) {
            medicineListView.adapter = reminderAdapter
        }
    }
}
