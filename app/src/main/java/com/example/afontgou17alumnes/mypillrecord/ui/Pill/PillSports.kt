package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.MainActivity
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.ActivityTherapy
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Frequency
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_pill_sports.*
import kotlinx.android.synthetic.main.activity_pill_sports.view.*
import kotlinx.android.synthetic.main.number_dialog.view.*
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.OK
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.cancel
import kotlinx.android.synthetic.main.time_dialog.view.*
import java.util.*


class PillSports : AppCompatActivity() {

    var new_activity=""
    var hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    var minute=Calendar.getInstance().get(Calendar.MINUTE)
    var timeListViewfrequency : ListView? = null
    var timeAdapter : PillHourListAdapter5? = null
    var w_hourListfrequency= mutableListOf<String>()
    var frequencyClass : Frequency? = null
    var duration:Int=0
    var notes:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.activity_pill_sports)
        text_view_frequency.movementMethod = ScrollingMovementMethod()
        //FROM FREQUENCY
        val bundle: Bundle? = intent?.extras
        val id_RadioButton = bundle?.get("RadioButton")
        if(id_RadioButton!=null){
            Log.e("id_RadioButton",id_RadioButton.toString())
            val From = bundle.get("From")
            val To = bundle.get("To")
            val Activity = bundle.get("Activity")
            val duration1 = bundle.get("Duration")
            if(Activity!=null){
                set_sports.text= Editable.Factory.getInstance().newEditable(Activity as CharSequence?)
                new_activity=Activity as String
            }
            //duration
            if(duration1!=null){
                this.duration = duration1 as Int
                btn_time.text=duration.toString()
            }
            //notes
            val Notes = bundle.get("Notes")
            if(Notes != null){
                this.notes = Notes as String
                val NotesNoum = findViewById<TextInputEditText>(R.id.input_notes)
                NotesNoum.text= Editable.Factory.getInstance().newEditable(Notes)
            }

            val RadioButtonValue = bundle.get("RadioButtonValue")
            //el frequency ha retornat algo btn_frequency.text=
            when(id_RadioButton){
                0->{
                    text_view_frequency.text= From.toString()+" to "+To.toString()
                    //Creem la classe frequency
                    val frequencyClass =
                        Frequency(
                            From as String,
                            To as String
                        )
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
                    Log.d("frequencyClass",frequencyClass.toString())
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
        set_sports.setOnClickListener {
            showPopupMenu(it)
        }
        btn_add_field_Sports.setOnClickListener {
            select_time()
        }
        image_view.setOnClickListener {
            val intent = Intent(this, Pillplanificar::class.java)
            startActivity(intent)
        }
        btn_frequency.setOnClickListener {
            val intent = Intent(this, PillFrequency::class.java)
            val llista = w_hourListfrequency.toTypedArray()
            val activity = new_activity
            val NotesNoum = findViewById<TextInputEditText>(R.id.input_notes)
            notes=NotesNoum.text.toString()
            intent.putExtra("Activity",activity)
            intent.putExtra("Hours",llista)
            intent.putExtra("From","PillSports")
            intent.putExtra("Duration",duration)
            intent.putExtra("Notes",notes)
            startActivity(intent)
        }
        btn_Save.setOnClickListener {
            save_activity()
           if (frequencyClass != null && !new_activity.equals("")){
               val NotesNoum = findViewById<TextInputEditText>(R.id.input_notes)
               notes=NotesNoum.text.toString()
               val freq:Frequency = frequencyClass!!
               val therapy= ActivityTherapy(freq,this.notes, Calendar.getInstance().timeInMillis.toString(),new_activity,this.duration, w_hourListfrequency as ArrayList<String>)
               Controller.addTherapy__CreateReminders(therapy)
               Toast.makeText(this, "New plan added", Toast.LENGTH_LONG).show()
               Log.e("THERAPY",therapy.toString())
               go_home()
           }
            else{
            Toast.makeText(this, "Missing Data", Toast.LENGTH_LONG).show()
           }
        }
        btn_time.setOnClickListener {
            select_timeSports()
        }

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

    private fun go_home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun go_back(){
        onBackPressed()
    }

    private fun save_activity(){
        this.new_activity=set_sports.text.toString()
    }//cal completar

    fun select_timeSports(){
        var new_dose=1
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.number_dialog, null)
        //Set Number Picker
        mDialogView.number_Picker.minValue = 1
        mDialogView.number_Picker.maxValue = 100
        mDialogView.number_Picker.wrapSelectorWheel = false

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Set duration")
        val mAlertDialog = mBuilder.show()
        mDialogView.number_Picker.setOnValueChangedListener { picker, oldVal, newVal ->
            new_dose=newVal
        }
        mDialogView.OK.setOnClickListener {
            this.duration=new_dose
            btn_time.text=duration.toString()
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
        mDialogView.cancel.setOnClickListener {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            mAlertDialog.dismiss()
        }
    }

    private fun showPopupMenu(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.activity_popup_menu, menu)
        setOnMenuItemClickListener { item ->
            Toast.makeText(view.context, "You Clicked : ${item.title}", Toast.LENGTH_SHORT).show()
            new_activity=item.title.toString()
            view.set_sports.setText(new_activity.toString())
            true
        }
        show()
    }
    fun listHasChanged( llista: MutableList<String>){
        val arraylocal = llista.toTypedArray()
        val timeListViewfrequency2: ListView? = findViewById(R.id.lw_hours_medications)
        val timeAdapter2: PillHourListAdapter5 = PillHourListAdapter5(this.baseContext, arraylocal,this)
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

    private fun binaryToWeek(array: Array<String>): MutableList<String> {
        var array2= mutableListOf<String>("Mon","Tue","Wed","Thu","Fri","Sat","Sun")
        for(x in 0..6){
            if(array[x]=="0"){
                array2[x]=""
            }
        }
        return array2
    }

    fun get_w_hourListfrequency(): MutableList<String> {
        return w_hourListfrequency
    }

}


