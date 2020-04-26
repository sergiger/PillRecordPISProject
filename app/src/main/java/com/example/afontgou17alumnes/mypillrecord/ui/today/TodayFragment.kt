package com.example.afontgou17alumnes.mypillrecord.ui.today

import android.R.attr
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
import com.example.afontgou17alumnes.mypillrecord.data.model.*
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.ReminderListAdapter
import kotlinx.android.synthetic.main.today__fragment.*
import java.time.LocalDate


/**
 * A simple [Fragment] subclass.
 */
class TodayFragment : Fragment() {
    lateinit var actualReminder : Reminder

    companion object {
        fun newInstance() =
            TodayFragment()
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
        //Ara es duplica pero quan es puguin guardar les dades s'eliminarà aquest metode
        Controller.setRemindersData()
        createTodayList()
        add_button.setOnClickListener {
            val mDialog = AddUnplannedEntry()
            mDialog.show(childFragmentManager, "Add unplanned entry")
        }
        today_list.setOnItemClickListener { adapterView, view, i, l ->
            var reminder : Reminder = adapterView.adapter.getItem(i) as Reminder
            actualReminder = reminder
            val intent = Intent(context, TodayModifyReminder::class.java)
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
                createTodayList()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //do nothing
            }
        }
    }

    fun createTodayList(){
        /*val medicineList = arrayOf(
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
        )*/
        val medicineListView : ListView? = view?.findViewById(R.id.today_list)
        val reminderAdapter = ReminderListAdapter(this, Controller.getRemindersByDateAndStatus(LocalDate.now(), ReminderStatus.TO_DO))
        if (medicineListView != null) {
            medicineListView.adapter = reminderAdapter
        }
    }
}
