package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_pill_medication.*
import kotlinx.android.synthetic.main.activity_pill_medication.view.*
import kotlinx.android.synthetic.main.activity_pill_sports.btn_Save
import kotlinx.android.synthetic.main.activity_pill_sports.btn_frequency
import kotlinx.android.synthetic.main.number_dialog.view.*
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
        val image_view = findViewById<ImageButton>(R.id.left_arrow)

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
        btn_frequency.setOnClickListener {
            val intent = Intent(this, PillFrequency::class.java)
            startActivity(intent)
        }
        btn_Save.setOnClickListener {
            Toast.makeText(this, "New plan added", Toast.LENGTH_LONG).show()
            save_medication()
            go_home()
        }

        // Barcode Scanner implementatiton
        btn_scan.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()
        }
        val pos = w_hourListfrequency.size
        Log.e("MINUTE",minute.toString())
        if(minute<10){
            val minute2 = "0"+ minute.toString()
            w_hourListfrequency.add(pos, hour.toString()+":"+ minute2)
        }
        else{
            w_hourListfrequency.add(pos, hour.toString()+":"+ minute)
        }
        //la llista ha canviat
        listHasChanged(w_hourListfrequency)
    }

    // Barcode Scanner implementatiton
    // Exemple: Aspirin (cut first and last number of the barcode)
    // https://api.fda.gov/drug/ndc.json?search=packaging.package_ndc:%220536-1149-41%22
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result:IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
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

    private fun showPopupMenu_units(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.dose_unitats_popup_menu, menu)
        setOnMenuItemClickListener { item ->
            Toast.makeText(view.context, "You Clicked : ${item.title}", Toast.LENGTH_SHORT).show()
            new_units=item.title.toString()
            view.btn_units.text = new_units.toString()
            true
        }
        show()
    }

    fun go_home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun go_back(){
        onBackPressed()
    }

    fun save_medication(){
        this.notes=input_notes.text.toString()
        //Controller.createMedicineTherapy()
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
        if(llista.size > 1){
            Toast.makeText(this, "Added", Toast.LENGTH_LONG).show()
        }
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
    //per afegir
    fun select_time(){
        var new_Hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        var new_minute= Calendar.getInstance().get(Calendar.MINUTE)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.time_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
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
    //per modificar
    fun select_time2(textView: TextView){
        var new_Hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        var new_minute= Calendar.getInstance().get(Calendar.MINUTE)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.time_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
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