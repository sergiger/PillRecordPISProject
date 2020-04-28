package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.pills.MyData
import com.example.afontgou17alumnes.mypillrecord.data.search.AsyncResponse
import com.example.afontgou17alumnes.mypillrecord.data.search.AsyncTaskHandler
import com.google.android.material.textfield.TextInputEditText
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

class PillMedication : AppCompatActivity() , AsyncResponse{
    var frequencyClass : Frequency? = null
    var medicine= ""
    var notes=""
    var new_units=""
    var dose :Int = 0

    var hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    var minute=Calendar.getInstance().get(Calendar.MINUTE)
    var timeListViewfrequency : ListView? = null
    var timeAdapter : PillHourListAdapter2? = null
    var w_hourListfrequency= mutableListOf<String>()

    var activityFrequency1 = ""

    // API Implementation
    val url = "https://api.fda.gov/drug/ndc.json?search=packaging.package_ndc:"
    val field = "packaging.package_ndc:"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_medication)

        val image_view = findViewById<ImageButton>(R.id.left_arrow)
        //extras
        val bundle:Bundle? = intent.extras
        //medicine
        val Medicine = bundle?.get("Medicine")
        if(Medicine!=null){
            this.medicine= Medicine as String
            val MedicineNoum = findViewById<TextInputEditText>(R.id.pill_search)
            MedicineNoum.text= Editable.Factory.getInstance().newEditable(Medicine)
        }
        //dose
        val Dose = bundle?.get("Dose")
        if(Dose != null){
            this.dose = Dose as Int
            btn_dose.text=dose.toString()
        }
        //units
        val Units = bundle?.get("Units")
        if(Units != null){
            this.new_units = Units as String
            btn_units.text = new_units.toString()
        }
        //notes
        val Notes = bundle?.get("Notes")
        if(Notes != null){
            this.notes = Notes as String
            val NotesNoum = findViewById<TextInputEditText>(R.id.input_notes)
            NotesNoum.text= Editable.Factory.getInstance().newEditable(Notes)
        }
        //Frequency
        val id_RadioButton = bundle?.get("RadioButton")
        if(id_RadioButton!=null){
            val From = bundle.get("From")
            val To = bundle.get("To")
            val RadioButtonValue = bundle.get("RadioButtonValue")
            //el frequency ha retornat algo btn_frequency.text=
            when(id_RadioButton){
                0->{
                    btn_frequency.text= From.toString()+" to "+To.toString()
                    //Creem la classe frequency
                    val frequencyClass =Frequency(From as String , To as String )
                    Log.w("frequencyClass",frequencyClass.toString())
                    this.frequencyClass=frequencyClass

                }
                1->{
                    btn_frequency.text= From.toString()+" to "+To.toString()+" each "+RadioButtonValue+" days"
                    //Creem la classe frequency
                    val eachdaydose =(RadioButtonValue as String).toInt()
                    val frequencyClass =Frequency(From as String , To as String, eachdaydose)
                    Log.w("frequencyClass",frequencyClass.toString())
                    this.frequencyClass=frequencyClass
                }
                2->{
                    var array = RadioButtonValue as Array<String>
                    var array2:MutableList<String>
                    array2=binaryToWeek(array )
                    btn_frequency.text= From.toString()+" to "+To.toString()+" at "+array2.toString()
                    //Creem la classe frequency
                    var array3 = array2.toTypedArray()
                    val frequencyClass =
                        Frequency(
                            From as String,
                            To as String,
                            array3
                        )
                    Log.w("frequencyClass",frequencyClass.toString())
                    this.frequencyClass=frequencyClass
                }
                3->{
                    Log.e("RadioButtonValue",RadioButtonValue.toString())
                    var dies = RadioButtonValue as Array<String>
                    btn_frequency.text= "dies: "+  dies.contentToString()
                    //Creem la classe frequency
                    val frequencyClass =
                        Frequency(
                            dies
                        )
                    Log.w("frequencyClass",frequencyClass.toString())
                    this.frequencyClass=frequencyClass
                }
            }
        }

        image_view.setOnClickListener {
            val intent = Intent(this, Pillplanificar::class.java)
            startActivity(intent)

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
            val llista = w_hourListfrequency.toTypedArray()
            val MedicineNoum = findViewById<TextInputEditText>(R.id.pill_search)
            medicine=MedicineNoum.text.toString()
            val NotesNoum = findViewById<TextInputEditText>(R.id.input_notes)
            notes=NotesNoum.text.toString()
            intent.putExtra("Medicine",medicine)
            intent.putExtra("Hours",llista)
            intent.putExtra("Dose",dose)
            intent.putExtra("Units",new_units)
            intent.putExtra("Notes",notes)
            intent.putExtra("From","PillMedication")
            startActivity(intent)

        }
        btn_Save.setOnClickListener {
            Toast.makeText(this, "New plan added", Toast.LENGTH_LONG).show()
            save_medication()
            Log.e("ACTIVITYFREQUENCY",activityFrequency1.toString())
            go_home()
        }

        // Barcode Scanner implementatiton
        btn_scan.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()
        }
        if(w_hourListfrequency.size == 0) {
            val pos = w_hourListfrequency.size
            Log.e("MINUTE", minute.toString())
            if (minute < 10) {
                val minute2 = "0" + minute.toString()
                w_hourListfrequency.add(pos, hour.toString() + ":" + minute2)
            } else {
                w_hourListfrequency.add(pos, hour.toString() + ":" + minute)
            }
            //la llista ha canviat
            listHasChanged(w_hourListfrequency)
        }
    }

    private fun binaryToWeek(array: Array<String>): MutableList<String> {
        var array2= mutableListOf<String>("Mon","Tue","Wed","Thu","Fri","Sat","Sun")
        for(x in 0..6){
            if(array[x]=="0"){
                array2[x]=""
            }
        }
        return array2
    }

    // Barcode Scanner implementatiton -> cut first and last number of the barcode
    // https://api.fda.gov/drug/ndc.json?search=packaging.package_ndc:0536-1149-41

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result:IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                    // API Implementation
                    var ndc = result.contents.substring(1,result.contents.length - 1)
                    var format1 = ndc.substring(0,4) + "-" + ndc.substring(4,8) + "-" + ndc.substring(8) // 4-4-2
                    var format2 = ndc.substring(0,5) + "-" + ndc.substring(5,8) + "-" + ndc.substring(8) // 5-3-2

                    AsyncTaskHandler().execute(url+format1+"+"+field+format2)  // Final of implementation
                    println(url+format1+"+"+field+format2)
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
    override fun getResults(result: ArrayList<MyData>) {
        var pill = result[0]
        println(pill)

        var name = pill.brand_name + " " + pill.active_ingredients[0].name
        var editable:Editable = Editable.Factory.getInstance().newEditable(name)
        //val MedicineNoum = findViewById<TextInputEditText>(R.id.pill_search)
        //MedicineNoum.text = editable

        var strength = pill.active_ingredients[0].strength
        val parts = strength.split(" ", "/")
        var dosis = parts[0]
        var unit = parts[1]
        // btn_dose.text = dosis
        // btn_units.text = unit
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