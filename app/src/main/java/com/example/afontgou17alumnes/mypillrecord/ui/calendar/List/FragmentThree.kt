package com.example.afontgou17alumnes.mypillrecord.ui.calendar.List

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.Reminder
import kotlinx.android.synthetic.main.week_details.*

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
        historic_list.setOnItemClickListener { adapterView, view, i, l ->
            var reminder : Reminder = adapterView.adapter.getItem(i) as Reminder
            actualReminder = reminder
            /*val intent = Intent(context, HistoricModify::class.java)
            intent.putExtra("Reminder", reminder)
            startActivityForResult(intent, 1)*/
        }
    }

    fun createMedicineList() {
        val medicineListView : ListView? = view?.findViewById(R.id.historic_list)
        val reminderAdapter : HistoricListAdapter = HistoricListAdapter(this, Controller.getRemindersData()) // Controller.getRemindersData()
        if (medicineListView != null) {
            medicineListView.adapter = reminderAdapter
        }
    }
}
