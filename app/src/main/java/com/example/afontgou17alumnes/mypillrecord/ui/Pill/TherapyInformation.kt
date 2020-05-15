package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.ActivityTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MeasurementTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MedicineTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import kotlinx.android.synthetic.main.number_dialog.view.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import kotlinx.android.synthetic.main.therapy_information.*
import kotlinx.android.synthetic.main.time_dialog.view.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


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
            4->{
                freqPunctualDaysSelected()
                createPunctualDaysList()
            }
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

        add_hour_therapy_information.setOnClickListener {
            addHour()
        }

        add_punctual_day.setOnClickListener {
            addDay()
        }

        info_button.setOnClickListener {
            infoButtonModifier()
        }

        each_days_therapy_information.setOnClickListener {
            eachXDaysModifier()
        }

        fromto_layout_therapy_information.setOnClickListener {
            fromToButton()
        }
    }

    fun createHoursList(){
        val hourListAdapter = HoursListAdapter(this, therapy.hours)
        hours_therapy_information.adapter = hourListAdapter
        justifyListViewHeightBasedOnChildren(hours_therapy_information)
    }

    fun createPunctualDaysList(){
        val punctualDaysListAdapter = PunctualDaysListAdapter(this, therapy.frequency.listofpuntualdays.toCollection(ArrayList()))
        punctual_days_listview.adapter = punctualDaysListAdapter
        justifyListViewHeightBasedOnChildren(punctual_days_listview)
    }

    fun refreshDaysList(daysList : ArrayList<String>){
        val array = Array<String>(daysList.size) {_ -> ""}
        daysList.toArray(array)
        therapy.frequency.listofpuntualdays = array
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
        fromto_layout_therapy_information.visibility =  View.VISIBLE
        each_days_therapy_information.visibility = View.GONE
        checkbox_days_layout.visibility = View.GONE
        punctual_days_layout.visibility = View.GONE
    }

    private fun freqEachXDaysSelected(){
        frquency_button_therapy_information.text = "Each X days"
        fromto_layout_therapy_information.visibility =  View.VISIBLE
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
        fromto_layout_therapy_information.visibility =  View.VISIBLE
        each_days_therapy_information.visibility = View.GONE
        checkbox_days_layout.visibility = View.VISIBLE
        checkDays()
        punctual_days_layout.visibility = View.GONE
    }

    private fun freqPunctualDaysSelected(){
        frquency_button_therapy_information.text =  "Punctual days"
        fromto_layout_therapy_information.visibility =  View.GONE
        each_days_therapy_information.visibility = View.GONE
        checkbox_days_layout.visibility = View.GONE
        punctual_days_layout.visibility = View.VISIBLE
    }

    private fun addHour(){
        var newHour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        var newMinute= Calendar.getInstance().get(Calendar.MINUTE)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.time_dialog, null)

        val ok = mDialogView.findViewById<Button>(R.id.OK)
        val cancel = mDialogView.findViewById<Button>(R.id.cancel)

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Set time")
        val mAlertDialog = mBuilder.show()
        mDialogView.time_Picker.setOnTimeChangedListener { view, hour, minute ->
            newHour = hour
            newMinute = minute
        }
        ok.setOnClickListener {
            therapy.hours.add("$newHour:$newMinute")
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
            createHoursList()
        }
        cancel.setOnClickListener {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }

    }

    private fun addDay(){
        var new_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        var new_month=Calendar.getInstance().get(Calendar.MONTH)+1
        var new_year=Calendar.getInstance().get(Calendar.YEAR)

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.date_dialoge, null)

        val ok = mDialogView.findViewById<Button>(R.id.OK)
        val cancel = mDialogView.findViewById<Button>(R.id.cancel)

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Set specific dates")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        val datePicker_ini = mDialogView.findViewById<DatePicker>(R.id.date_Picker)
        val today = Calendar.getInstance()
        datePicker_ini.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            new_day=day
            new_month=month
            new_year=year
        }
        //Aqui faig els listeners dels dos botons
        ok.setOnClickListener {
            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show()
            val array = therapy.frequency.listofpuntualdays.toCollection(ArrayList())
            array.add("$new_day/$new_month/$new_year")
            refreshDaysList(array)
            createPunctualDaysList()
            mAlertDialog.dismiss()
        }
        cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
    }

    private fun infoButtonModifier(){
        var infoValue = 0
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.number_dialog, null)
        //Set Number Picker
        mDialogView.number_Picker.minValue = 1
        mDialogView.number_Picker.maxValue = 100
        mDialogView.number_Picker.wrapSelectorWheel = false

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        //Set title of dialog
        if(therapy is MedicineTherapy)  mBuilder.setTitle("Set dose")
        else mBuilder.setTitle("Set duration")

        val mAlertDialog = mBuilder.show()
        val ok = mDialogView.findViewById<Button>(R.id.OK)
        val cancel = mDialogView.findViewById<Button>(R.id.cancel)

        mDialogView.number_Picker.setOnValueChangedListener { picker, oldVal, newVal ->
            infoValue = newVal
        }
        ok.setOnClickListener {
            if(therapy is MedicineTherapy){
                (therapy as MedicineTherapy).dose = infoValue
                info_button.text = (therapy as MedicineTherapy).dose.toString()
            }
            else if(therapy is ActivityTherapy){
                (therapy as ActivityTherapy).duration = infoValue
                info_button.text = (therapy as ActivityTherapy).duration.toString() + " min"
            }
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
        cancel.setOnClickListener {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
    }

    private fun eachXDaysModifier(){
        var infoValue = 0
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.number_dialog, null)
        //Set Number Picker
        mDialogView.number_Picker.minValue = 1
        mDialogView.number_Picker.maxValue = 100
        mDialogView.number_Picker.wrapSelectorWheel = false

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        //Set title of dialog
        if(therapy is MedicineTherapy)  mBuilder.setTitle("Set dose")
        else mBuilder.setTitle("Set duration")

        val mAlertDialog = mBuilder.show()
        val ok = mDialogView.findViewById<Button>(R.id.OK)
        val cancel = mDialogView.findViewById<Button>(R.id.cancel)

        mDialogView.number_Picker.setOnValueChangedListener { picker, oldVal, newVal ->
            infoValue = newVal
        }
        ok.setOnClickListener {
            therapy.frequency.eachtimedose = infoValue
            each_days_therapy_information.text = "Each $infoValue day(s)"
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
        cancel.setOnClickListener {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
    }

    fun checkDays(){
        val boxes = arrayOf(checkBox_monday, checkBox_tuesday, checkBox_wednesday, checkBox_thursday,
            checkBox_friday, checkBox_saturday, checkBox_sunday)
        for( i in therapy.frequency.specificweekdays.indices){
            if(therapy.frequency.specificweekdays[i] != "")
                boxes[i].isChecked = true
        }
    }

    fun fromToButton(){
        var ini_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        var ini_month=Calendar.getInstance().get(Calendar.MONTH)+1
        var ini_year=Calendar.getInstance().get(Calendar.YEAR)
        var end_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        var end_month=Calendar.getInstance().get(Calendar.MONTH)+1
        var end_year=Calendar.getInstance().get(Calendar.YEAR)


        val mDialogView = LayoutInflater.from(this).inflate(R.layout.specific_dates_dialoge, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Set specific dates")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        val ok = mDialogView.findViewById<Button>(R.id.OK)
        val cancel = mDialogView.findViewById<Button>(R.id.cancel)

        val datePicker_ini = mDialogView.findViewById<DatePicker>(R.id.date_Picker_ini)
        val today = Calendar.getInstance()
        datePicker_ini.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            ini_day=day
            ini_month=month
            ini_year=year
        }
        val datePicker_end = mDialogView.findViewById<DatePicker>(R.id.date_Picker_end)
        datePicker_end.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            end_day=day
            end_month=month
            end_year=year
        }

        //Aqui faig els listeners dels dos botons
        ok.setOnClickListener {
            val i = set_ok_date(ini_day,ini_month,ini_year,end_day,end_month,end_year)
            if(i == 0){
                mAlertDialog.dismiss()
            }
            else{
                Toast.makeText(applicationContext,"Invalid date", Toast.LENGTH_SHORT).show()
            }
        }
        cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
    }

    fun set_ok_date(ini_day:Int,ini_month:Int,ini_year:Int,end_day:Int,end_month:Int,end_year:Int): Int {

        var data_ini=ini_day.toString()+"/"+ini_month.toString()+"/"+ini_year.toString()


        var data_end=end_day.toString()+"/"+end_month.toString()+"/"+end_year.toString()
        val dateStart = LocalDate.parse(data_ini, DateTimeFormatter.ofPattern("d/M/y"))
        val dateEnd = LocalDate.parse(data_end, DateTimeFormatter.ofPattern("d/M/y"))
        if(dateStart<=dateEnd){
            from_therapy_information.text = data_ini
            to_therapy_information.text = data_end
            return 0
        }
        else{
            return 1
        }
    }
}
