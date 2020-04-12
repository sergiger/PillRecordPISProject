package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_add_unplanned_medicine.*
import kotlinx.android.synthetic.main.activity_pill_medication.*
import kotlinx.android.synthetic.main.activity_pill_medication.view.*
import kotlinx.android.synthetic.main.activity_pill_sports.*
import kotlinx.android.synthetic.main.activity_pill_sports.btn_Save
import kotlinx.android.synthetic.main.activity_pill_sports.btn_frequency
import kotlinx.android.synthetic.main.activity_pill_sports.btn_from
import kotlinx.android.synthetic.main.fragment_pillfrequency__one.*
import kotlinx.android.synthetic.main.gender_dialoge.view.*
import kotlinx.android.synthetic.main.number_dialog.view.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.OK
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.cancel
import kotlinx.android.synthetic.main.time_dialog.view.*
import java.util.*

class PillMedication : AppCompatActivity() {

    var ini_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var ini_month=Calendar.getInstance().get(Calendar.MONTH)+1
    var ini_year=Calendar.getInstance().get(Calendar.YEAR)
    var end_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var end_month=Calendar.getInstance().get(Calendar.YEAR)
    var end_year=Calendar.getInstance().get(Calendar.YEAR)
    var medicine= ""
    var notes=""
    var new_units=""
    var dose=1

    var hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    var minute=Calendar.getInstance().get(Calendar.MINUTE)
    var timeListViewfrequency : ListView? = null
    var timeAdapter : PillHourListAdapter2? = null
    var w_hourListfrequency= mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_medication)
        val image_view = findViewById(R.id.left_arrow) as ImageButton

        btn_from.setText("")

        image_view.setOnClickListener {
            go_back()
        }
        btn_add_field_medication_button.setOnClickListener {
            select_time()
        }
        btn_dose.setOnClickListener {
            select_dose()
        }
        btn_units.setOnClickListener {
            showPopupMenu_units(it)
        }
        btn_from.setOnClickListener {
            select_start_end_dates()
        }
        btn_frequency.setOnClickListener {
            val intent = Intent(this, PillFrequency::class.java)
            startActivity(intent)
        }
        btn_Save.setOnClickListener {
            Toast.makeText(this, "New plan added", Toast.LENGTH_LONG).show()
            save_medication()
            go_home()
        }
        btn_scan.setOnClickListener {
            Toast.makeText(this, "TODO", Toast.LENGTH_LONG).show()
        }
        val pos = w_hourListfrequency.size
        w_hourListfrequency.add(pos, "08:00")
        //la llista ha canviat
        listHasChanged(w_hourListfrequency)

    }

    fun select_dose(){
        var new_dose=1
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.number_dialog, null)
        //Set Number Picker
        mDialogView.number_Picker.minValue = 1
        mDialogView.number_Picker.maxValue = 100
        mDialogView.number_Picker.wrapSelectorWheel = false

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Set dose")
        val mAlertDialog = mBuilder.show()
        mDialogView.number_Picker.setOnValueChangedListener { picker, oldVal, newVal ->
            new_dose=newVal
        }
        mDialogView.OK.setOnClickListener {
            this.dose=new_dose
            btn_dose.text=dose.toString()
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
    }

    fun select_start_end_dates(){
        var new_ini_day=this.ini_day
        var new_ini_month=this.ini_month
        var new_ini_year=this.ini_year
        var new_end_day=this.end_day
        var new_end_month=this.end_month
        var new_end_year=this.end_year


        val mDialogView = LayoutInflater.from(this).inflate(R.layout.specific_dates_dialoge, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Set specific dates")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //Aqui guardo la data INI seleccionada
        //i també poso la data correcta al DatePicker de INICI amb la data d'avui
        val datePicker_ini = mDialogView.findViewById<DatePicker>(R.id.date_Picker_ini)
        val today = Calendar.getInstance()
        datePicker_ini.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            new_ini_day=day
            new_ini_month=month
            new_ini_year=year
            //val msg = "You Selected: $day/$month/$year"
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        //Aqui guardo la data END seleccionada
        //i també poso la data correcta al DatePicker de END amb la data d'avui
        val datePicker_end = mDialogView.findViewById<DatePicker>(R.id.date_Picker_end)
        datePicker_end.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            new_end_day=day
            new_end_month=month
            new_end_year=year
            //val msg = "You Selected: $day/$month/$year"
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        //Aqui faig els listeners dels dos botons
        mDialogView.OK.setOnClickListener {
            Toast.makeText(this,"work in progress",Toast.LENGTH_LONG).show()
            set_ok_date(new_ini_day,new_ini_month,new_ini_year,new_end_day,new_end_month,new_end_year)
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
    }

    fun set_ok_date(ini_day:Int,ini_month:Int,ini_year:Int,end_day:Int,end_month:Int,end_year:Int){
        this.end_day=end_day
        this.end_month=end_month
        this.end_year=end_year

        var data_ini=this.ini_day.toString()+"//"+this.ini_month.toString()+"//"+this.ini_year.toString()

        this.ini_day=ini_day
        this.ini_month=ini_month
        this.ini_year=ini_year

        var data_end=this.end_day.toString()+"//"+this.end_month.toString()+"//"+this.end_year.toString()

        btn_from.setText(data_ini+" to "+data_end)
    }

    private fun showPopupMenu_units(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.dose_unitats_popup_menu, menu)
        setOnMenuItemClickListener { item ->
            Toast.makeText(view.context, "You Clicked : ${item.title}", Toast.LENGTH_SHORT).show()
            new_units=item.title.toString()
            view.btn_units.setText(new_units.toString())
            true
        }
        show()
    }

    fun go_home(){
        val intent = Intent(this, MainActivity::class.java);
        startActivity(intent);
    }

    fun go_back(){
        onBackPressed()
    }

    fun save_medication(){
        this.notes=input_notes.text.toString()
    }//cal completar

    fun listHasChanged( llista: MutableList<String>){
        val arraylocal = llista.toTypedArray()
        val timeListViewfrequency2: ListView? = findViewById(R.id.lw_hours_medications)
        val timeAdapter2: PillHourListAdapter2 = PillHourListAdapter2(this.baseContext, arraylocal,this)
        if (timeListViewfrequency2 != null) {
            timeListViewfrequency2.adapter = timeAdapter2
        }
        timeListViewfrequency = timeListViewfrequency2
        Log.e("timeListViewfrequency", timeListViewfrequency.toString())
        timeAdapter = timeAdapter2
        Toast.makeText(this, "Added", Toast.LENGTH_LONG).show()
        timeListViewfrequency?.setOnItemClickListener { adapterView, view, i, l ->
            Log.e("POSITION", timeAdapter?.getItem(i).toString())
            var text : TextView = view.findViewById(R.id.tw_hour)
            select_time2(text)

            /*
            var button: Button = view.findViewById(R.id.btn_delete_button)
            button.setOnClickListener {
                llista.removeAt(i)
                listHasChanged(llista)
            }


            if( button.visibility ==View.INVISIBLE){
                button.visibility=View.VISIBLE
                button.setOnClickListener {
                    llista.removeAt(i)
                    listHasChanged(llista)
                }
            }
            else{
                button.visibility=View.INVISIBLE
            }
             */
        }
    }

    fun select_time(){
        var new_Hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        var new_minute= Calendar.getInstance().get(Calendar.MINUTE)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.time_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this!!)
            .setView(mDialogView)
            .setTitle("Set date")
        val mAlertDialog = mBuilder.show()
        mDialogView.time_Picker.setOnTimeChangedListener { view, hour, minute ->
            new_Hour=hour
            new_minute=minute
        }
        mDialogView.OK.setOnClickListener {
            this.hour=new_Hour
            this.minute=new_minute
            var min=this.minute.toString()
            var hou=this.hour.toString()
            if(this.minute<10){
                min="0"+this.minute.toString()
            }
            if(this.hour<10){
                hou="0"+this.hour.toString()
            }
            //Aqui és on s'ha de posar on vols que s'escrigui el temps
            val pos = w_hourListfrequency.size
            if (w_hourListfrequency.contains(hou+":"+min)){
                Toast.makeText(this, "Time " +hou+":"+min +" alredy added", Toast.LENGTH_LONG).show()
            }
            else {
                w_hourListfrequency.add(pos, hou + ":" + min)
                //la llista ha canviat
                listHasChanged(w_hourListfrequency)
                mAlertDialog.dismiss()
            }
        }
        mDialogView.cancel.setOnClickListener {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
    }
    fun select_time2(textView: TextView){
        var new_Hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        var new_minute= Calendar.getInstance().get(Calendar.MINUTE)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.time_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this!!)
            .setView(mDialogView)
            .setTitle("Set date")
        val mAlertDialog = mBuilder.show()
        mDialogView.time_Picker.setOnTimeChangedListener { view, hour, minute ->
            new_Hour=hour
            new_minute=minute
        }
        mDialogView.OK.setOnClickListener {
            this.hour=new_Hour
            this.minute=new_minute
            var min=this.minute.toString()
            var hou=this.hour.toString()
            if(this.minute<10){
                min="0"+this.minute.toString()
            }
            if(this.hour<10){
                hou="0"+this.hour.toString()
            }
            //Aqui és on s'ha de posar on vols que s'escrigui el temps
            val pos = w_hourListfrequency.indexOf(textView.text)
            if (w_hourListfrequency.contains(hou+":"+min)){
                Toast.makeText(this, "Time " +hou+":"+min +" alredy added", Toast.LENGTH_LONG).show()
            }
            else {
                w_hourListfrequency.set(pos, hou + ":" + min)
                textView.text = hou + ":" + min
                //la llista ha canviat
                //listHasChanged(w_hourListfrequency)
                mAlertDialog.dismiss()
            }
        }
        mDialogView.cancel.setOnClickListener {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
    }
    fun get_w_hourListfrequency(): MutableList<String> {
        return w_hourListfrequency
    }




}
