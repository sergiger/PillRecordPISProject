package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.ActivityTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MeasurementTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MedicineTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import kotlinx.android.synthetic.main.therapy_information.*


class TherapyInformation : AppCompatActivity() {
    lateinit var therapy : Therapy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.therapy_information)

        //Therapy
        therapy = intent.extras.get("Therapy") as Therapy

        //View elements
        name_therapy_list.text = therapy.getName()
        input_note_therapy_infromation.setText(therapy.notes, TextView.BufferType.EDITABLE)
        from_therapy_information.text = therapy.frequency.startDate
        to_therapy_information.text = therapy.frequency.endDate
        createHoursList()

        if(therapy is MedicineTherapy){
            info_label.text = "Dose"
            info_button.text = (therapy as MedicineTherapy).dose.toString()
            unit_button_therapy_information.text = (therapy as MedicineTherapy).units
        }else if(therapy is MeasurementTherapy){
            //Treiem modificador d'informacio
            info_label.visibility = View.GONE
            info_button.visibility = View.GONE

            //Treiem modificador d'unitats
            unit_label.visibility = View.GONE
            unit_button_therapy_information.visibility = View.GONE
        }else if(therapy is ActivityTherapy){
            info_label.text = "Duration"
            info_button.text = (therapy as ActivityTherapy).duration.toString() + " min"

            //Treiem modificador d'unitats
            unit_label.visibility = View.GONE
            unit_button_therapy_information.visibility = View.GONE
        }

        //Frequency
        when(therapy.frequency.type){
            1->freqDailySelected()
            2->freqEachXDaysSelected()
            3->freqSpecificDaysSelected()
            4->freqPunctualDaysSelected()
        }

        //Listeners
        back_arrow.setOnClickListener{
            onBackPressed()
        }

        frquency_button_therapy_information.setOnClickListener {
            showPopUpMenuFrequency(it)
        }

        unit_button_therapy_information.setOnClickListener {
            showPopUpMenuUnits(it)
        }
    }

    fun createHoursList(){
        val hourListAdapter = HoursListAdapter(this, therapy.hours)
        hours_therapy_information.adapter = hourListAdapter
        justifyListViewHeightBasedOnChildren(hours_therapy_information)
    }

    fun createPunctualDaysList(){
        val punctualDaysListAdapter = PunctualDaysListAdapter(this, therapy.frequency.listofpuntualdays)
        punctual_days_listview.adapter = punctualDaysListAdapter
        justifyListViewHeightBasedOnChildren(punctual_days_listview)
    }

    private fun justifyListViewHeightBasedOnChildren(listView: ListView) {
        val adapter = listView.adapter ?: return
        val vg: ViewGroup = listView
        var totalHeight = 0
        for (i in 0 until adapter.count) {
            val listItem = adapter.getView(i, null, vg)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }
        val par = listView.layoutParams
        par.height = totalHeight + listView.dividerHeight * (adapter.count - 1)
        listView.layoutParams = par
        listView.requestLayout()
    }

    private fun showPopUpMenuFrequency(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.frequency_type_popup_menu, menu)
        setOnMenuItemClickListener {item ->
            frquency_button_therapy_information.text = item.title
            when(item.title){
                "Daily"->freqDailySelected()
                "Each X days"->freqEachXDaysSelected()
                "Specific week days"->freqSpecificDaysSelected()
                "Punctual days"->freqPunctualDaysSelected()
            }
            true
        }
        show()
    }

    private fun  showPopUpMenuUnits(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.dose_unitats_popup_menu, menu)
        setOnMenuItemClickListener{item ->
            unit_button_therapy_information.text = item.title
            (therapy as MedicineTherapy).units = item.title.toString()
            true
        }
        show()
    }

    private fun freqDailySelected(){
        frquency_button_therapy_information.text = "Daily"
        each_days_therapy_information.visibility = View.GONE
        checkbox_days_layout.visibility = View.GONE
        punctual_days_layout.visibility = View.GONE
    }

    private fun freqEachXDaysSelected(){
        frquency_button_therapy_information.text = "Each X days"
        each_days_therapy_information.visibility = View.VISIBLE
        if(therapy.frequency.eachtimedose == 0)
            each_days_therapy_information.text = "Each X day(s)"
        else
            each_days_therapy_information.text = "Each ${therapy.frequency.eachtimedose} day(s)"

        checkbox_days_layout.visibility = View.GONE
        punctual_days_layout.visibility = View.GONE
    }

    private fun freqSpecificDaysSelected(){
        frquency_button_therapy_information.text = "Specific week days"
        each_days_therapy_information.visibility = View.GONE
        checkbox_days_layout.visibility = View.VISIBLE
        punctual_days_layout.visibility = View.GONE
    }

    private fun freqPunctualDaysSelected(){
        frquency_button_therapy_information.text =  "Punctual days"
        each_days_therapy_information.visibility = View.GONE
        checkbox_days_layout.visibility = View.GONE
        punctual_days_layout.visibility = View.VISIBLE
    }
}
