package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Frequency
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.MeasurementTherapy
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.pill_mesurements_activity.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.OK
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.cancel
import kotlinx.android.synthetic.main.time_dialog.view.*
import java.util.*

class Pill_mesurements_info : AppCompatActivity() {

    //de la mateixa classe
    var hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    var minute=Calendar.getInstance().get(Calendar.MINUTE)
    var timeListViewfrequency : ListView? = null
    var timeAdapter : PillHourListAdapter4? = null
    var w_hourListfrequency= mutableListOf<String>()
    var titol=""
    var unitats=""
    var frequencyClass : Frequency? = null
    var notes:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pill_mesurements_activity)

        //agafem titol i unitats
        val bundle:Bundle = intent.extras
        text_view_frequency.movementMethod = ScrollingMovementMethod()
        val title = bundle.get("TitolMesurement")
        titol=title as String
        val unitat =bundle.get("UnitatsMesurement")
        unitats=unitat as String
        toolbar_title.text = title.toString()
        unitats_output.text=unitat.toString()
        //FROM FREQUENCY
        val id_RadioButton = bundle.get("RadioButton")
        if(id_RadioButton!=null){
            Log.e("id_RadioButton",id_RadioButton.toString())
            val From = bundle.get("From")
            val To = bundle.get("To")
            val RadioButtonValue = bundle.get("RadioButtonValue")
            //notes
            val Notes = bundle.get("Notes")
            if(Notes != null){
                this.notes = Notes as String
                val NotesNoum = findViewById<TextInputEditText>(R.id.input_notes)
                NotesNoum.text= Editable.Factory.getInstance().newEditable(Notes)
            }

            //el frequency ha retornat algo btn_frequency.text=
            when(id_RadioButton){
                0->{
                    text_view_frequency.text= From.toString()+" to "+To.toString()
                    //Creem la classe frequency
                    val frequencyClass = Frequency(From as String,To as String)
                    Log.w("frequencyClass",frequencyClass.toString())
                    this.frequencyClass=frequencyClass
                }
                1->{
                    text_view_frequency.text= From.toString()+" to "+To.toString()+" each "+RadioButtonValue+" days"
                    //Creem la classe frequency
                    val eachdaydose =(RadioButtonValue as String).toInt()
                    val frequencyClass =
                        Frequency(
                            From as String,
                            To as String,
                            eachdaydose
                        )
                    Log.w("frequencyClass",frequencyClass.toString())
                    this.frequencyClass=frequencyClass
                }
                2->{
                    var array = RadioButtonValue as Array<String>
                    //val array: Any? = RadioButtonValue
                    var array2:MutableList<String>
                    array2=binaryToWeek(array )
                    text_view_frequency.text= From.toString()+" to "+To.toString()+" at "+array2.toString()
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
                    text_view_frequency.text= "dies: "+  dies.contentToString()
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

        val image_view = findViewById<ImageButton>(R.id.left_arrow)
        image_view.setOnClickListener {
            val intent = Intent(this, PillMesurements::class.java)
            startActivity(intent)
        }
        btn_add_field_medication_button.setOnClickListener {
            select_time()
        }
        btn_frequency.setOnClickListener {
            val intent = Intent(this, PillFrequency::class.java)
            //d'on vinc i informació
            val llista = w_hourListfrequency.toTypedArray()
            val NotesNoum = findViewById<TextInputEditText>(R.id.input_notes)
            notes=NotesNoum.text.toString()
            intent.putExtra("Hours",llista)
            intent.putExtra("From","PillMesurements")
            intent.putExtra("TitolMesurement",titol)
            intent.putExtra("UnitatsMesurement",unitats)
            intent.putExtra("Notes",notes)
            startActivity(intent)
        }
        btn_Save.setOnClickListener {
            save_mesurement()
            if (frequencyClass != null ){
                val NotesNoum = findViewById<TextInputEditText>(R.id.input_notes)
                notes=NotesNoum.text.toString()
                val freq:Frequency = frequencyClass!!
                val therapy= MeasurementTherapy(freq,notes, Controller.user.id,titol, w_hourListfrequency as ArrayList<String>)
                Controller.addTherapy__CreateReminders(therapy)
                Toast.makeText(this, "New plan added", Toast.LENGTH_LONG).show()
                Log.e("THERAPY",therapy.toString())
                go_home()
            }
            else{
                Toast.makeText(this, "Missing Data", Toast.LENGTH_LONG).show()
            }
        }

        //si passem hora
        /*
        val Hours = bundle?.get("Hours")
        if(Hours != null){
            w_hourListfrequency= (Hours as Array<String>).toList() as MutableList<String>
            listHasChanged(w_hourListfrequency)
        }*/
        //inicialitzem la llista d'hores si no hi ha res
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

    fun go_home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun go_back(){
        onBackPressed()
    }

    fun save_mesurement(){}//cal completar
    private fun binaryToWeek(array: Array<String>): MutableList<String> {
        var array2= mutableListOf<String>("Mon","Tue","Wed","Thu","Fri","Sat","Sun")
        for(x in 0..6){
            if(array[x]=="0"){
                array2[x]=""
            }
        }
        return array2
    }

    //llista
    fun listHasChanged( llista: MutableList<String>){
        val arraylocal = llista.toTypedArray()
        val timeListViewfrequency2: ListView? = findViewById(R.id.lw_hours_medications)
        val timeAdapter2: PillHourListAdapter4 = PillHourListAdapter4(this.baseContext, arraylocal,this)
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



