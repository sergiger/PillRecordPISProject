package com.example.afontgou17alumnes.mypillrecord.ui.calendar.List

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.Reminder
import kotlinx.android.synthetic.main.fragment_three.*
import java.time.LocalDate

/**
 * A simple [Fragment] subclass.
 */
class FragmentThree : Fragment() {
    lateinit var actualReminder : Reminder
    companion object {
        fun newInstance() = FragmentThree()
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
        historic_list.setOnItemClickListener { adapterView, view, i, l ->
            var reminder : Any = adapterView.adapter.getItem(i)
            if (reminder is LocalDate) Toast.makeText(context,"Date", Toast.LENGTH_SHORT).show()
            else if (reminder is Reminder){
                actualReminder = reminder
                Toast.makeText(context,"Reminder", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, HistoricModify::class.java)
                intent.putExtra("Reminder", reminder)
                startActivityForResult(intent, 1)
            }
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
                actualizeDB()
                createMedicineList()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //do nothing
            }
        }
    }
    fun createMedicineList() {
        // val medicineList = Controller.getRemindersData()
        val medicineList = Controller.getHistoricDatesAndReminders() // New
        val medicineListView : ListView? = view?.findViewById(R.id.historic_list)
        // New
        val reminderAdapter : CustomAdapter = CustomAdapter(this.context, medicineList) // Controller.getRemindersData()
        if (medicineListView != null) {
            medicineListView.adapter = reminderAdapter
        }
    }
    fun actualizeDB(){
        Controller.controllerSharePrefs.sharedUpLoadReminders()
        Controller.RemindersToFirebase()
    }
}
