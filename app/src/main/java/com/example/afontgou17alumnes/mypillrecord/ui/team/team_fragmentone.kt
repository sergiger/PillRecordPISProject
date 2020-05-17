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
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.Week.PlannedAdapter
import kotlinx.android.synthetic.main.fragment_one.*

/**
 * A simple [Fragment] subclass.
 */
class team_fragmentone : Fragment() {
    lateinit var actualReminder : Reminder
    companion object {
        fun newInstance() = team_fragmentone()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createPlannedList()
        week_list.isClickable = false

    }

    fun createPlannedList() {
        val medicineList = Controller.team_calendar_getPlannedDatesAndReminders() // New
        val medicineListView : ListView? = view?.findViewById(R.id.week_list)
        val reminderAdapter : PlannedAdapter = PlannedAdapter(this.context, medicineList) // Controller.getRemindersData()
        if (medicineListView != null) {
            medicineListView.adapter = reminderAdapter
        }
    }

}
