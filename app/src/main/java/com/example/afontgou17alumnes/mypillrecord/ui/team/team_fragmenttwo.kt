package com.example.afontgou17alumnes.mypillrecord.ui.team


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.UnplannedMedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.MonthListAdapter
import kotlinx.android.synthetic.main.fragment_two.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class team_fragmenttwo : Fragment() {

    val c = Calendar.getInstance()
    var day = c.get(Calendar.DAY_OF_MONTH)
    var month = c.get(Calendar.MONTH)+1
    var year= c.get(Calendar.YEAR)
    var iniciat=false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createMonthList()
        month-=1
        createCalendarDialog()
    }

    @SuppressLint("SetTextI18n")
    fun createCalendarDialog() {
        val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener {
                view, myear, monthOfYear, dayOfMonth ->
            val monthOfYear = monthOfYear+1
            txt_datePicked.text = "$dayOfMonth/$monthOfYear/$myear"
            day = dayOfMonth
            month = monthOfYear
            year = myear

            createMonthList()
        }, year, month, day)
        select_date_month_layout.setOnClickListener {
            datePickerDialog.show()
        }

    }

    fun createMonthList() {
        val medicineList = Controller.team_calendar_getRemindersByDate(LocalDate.of(year,month,day))

        val medicineListView : ListView? = view?.findViewById(R.id.month_list)
        val medicineAdapter = MonthListAdapter(this, medicineList)
        if (medicineListView != null) {
            medicineListView.adapter = medicineAdapter
        }

    }
}