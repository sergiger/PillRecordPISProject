package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.ActivityTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MeasurementTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MedicineTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import kotlinx.android.synthetic.main.therapy_information.*
import kotlinx.android.synthetic.main.therapy_information.back_arrow
import kotlinx.android.synthetic.main.therapy_information.info_button

class TherapyInformation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.therapy_information)

        //Therapy
        var therapy = intent.extras.get("Therapy") as Therapy

        //View elements
        name_therapy_list.text = therapy.getName()
        if(therapy is MedicineTherapy){
            info_label.text = "Dose"
            info_button.text = therapy.dose.toString()
            unit_button_therapy_information.text = therapy.units
        }else if(therapy is MeasurementTherapy){
            //Treiem modificador d'informacio
            info_label.visibility = View.GONE
            info_button.visibility = View.GONE

            //Treiem modificador d'unitats
            unit_label.visibility = View.GONE
            unit_button_therapy_information.visibility = View.GONE
        }else if(therapy is ActivityTherapy){
            info_label.text = "Duration"
            info_button.text = therapy.duration.toString() + " min"

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

    private fun freqDailySelected(){
        frquency_button_therapy_information.text = "Daily"
        each_days_therapy_information.visibility = View.GONE
        checkbox_days_layout.visibility = View.GONE
    }

    private fun freqEachXDaysSelected(){
        frquency_button_therapy_information.text = "Each X days"
        each_days_therapy_information.visibility = View.VISIBLE
        checkbox_days_layout.visibility = View.GONE
    }

    private fun freqSpecificDaysSelected(){
        frquency_button_therapy_information.text = "Specific week days"
        each_days_therapy_information.visibility = View.GONE
        checkbox_days_layout.visibility = View.VISIBLE
    }

    private fun freqPunctualDaysSelected(){
        frquency_button_therapy_information.text =  "Punctual days"
        each_days_therapy_information.visibility = View.GONE
        checkbox_days_layout.visibility = View.GONE
    }
}
