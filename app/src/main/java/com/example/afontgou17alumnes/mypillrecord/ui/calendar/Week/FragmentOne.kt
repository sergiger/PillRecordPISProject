package com.example.afontgou17alumnes.mypillrecord.ui.calendar.Week

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
import kotlinx.android.synthetic.main.fragment_one.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentOne : Fragment() {
    lateinit var actualReminder : Reminder
    companion object {
        fun newInstance() = FragmentOne()
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
        createWeekList()
        week_list.setOnItemClickListener { adapterView, view, i, l ->
            var reminder : Reminder = adapterView.adapter.getItem(i) as Reminder
            actualReminder = reminder
            val intent = Intent(context, WeekModify::class.java)
            intent.putExtra("Reminder", reminder)
            startActivityForResult(intent, 1)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1){
            if (resultCode == Activity.RESULT_OK) {
                val result = data?.getSerializableExtra("Reminder") as Reminder
                actualReminder.status = result.status
                actualReminder.time = result.time
                when(actualReminder){
                    is MedicineReminder -> (actualReminder as MedicineReminder).dose = (result as MedicineReminder).dose
                    is MeasurementReminder -> (actualReminder as MeasurementReminder).value = (result as MeasurementReminder).value
                    is ActivityReminder -> (actualReminder as ActivityReminder).duration = (result as ActivityReminder).duration
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //do nothing
            }
        }
    }
    fun createWeekList() {

        val medicineListView : ListView? = view?.findViewById(R.id.week_list)
        val reminderAdapter : WeekListAdapter = WeekListAdapter(this, Controller.getRemindersData())
        if (medicineListView != null) {
            medicineListView.adapter = reminderAdapter
        }
    }
}
