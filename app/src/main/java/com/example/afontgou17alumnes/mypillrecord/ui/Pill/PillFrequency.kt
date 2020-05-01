package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_pill_frequency.*
import kotlinx.android.synthetic.main.share_team_activity.radio_group
import kotlinx.android.synthetic.main.specific_dates_dialoge.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PillFrequency : AppCompatActivity() {
    //values from pillFrequency
    var ini_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var ini_month=Calendar.getInstance().get(Calendar.MONTH)+1
    var ini_year=Calendar.getInstance().get(Calendar.YEAR)
    var end_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var end_month=Calendar.getInstance().get(Calendar.MONTH)+1
    var end_year=Calendar.getInstance().get(Calendar.YEAR)

    var button_radiogrupPressed = ""
    var new_dose=1
    var arrayDiesSaltejats= mutableListOf<String>()
    var arrayDiesSetmana= mutableListOf<String>("0","0","0","0","0","0","0")

    //values from PillMedication
    var Medicine = ""
    var DoseIn = 0
    var Units = ""
    var Notes = ""
    var Hours = listOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_frequency)
        //agafem el que passem
        val bundle:Bundle? = intent.extras
        val Medicine = bundle?.get("Medicine")
        if(Medicine!= null){
            this.Medicine= Medicine as String
        }
        val Hours = bundle?.get("Hours")
        if(Hours!=null){
            this.Hours = (Hours as Array<String>).toList()
        }
        val Dose = bundle?.get("Dose")
        if(Dose!= null){
            this.DoseIn = Dose as Int
        }
        val Units = bundle?.get("Units")
        if(Units != null){
            this.Units = Units as String
        }
        val Notes = bundle?.get("Notes")
        if(Notes!= null){
            this.Notes = Notes as String
        }
        Log.e("Medicine",this.Medicine)
        Log.e("Hours",this.Hours.toString())
        Log.e("Dose",this.DoseIn.toString())
        Log.e("Units",this.Units)
        Log.e("Notes",this.Notes)
        /*
        intent.putExtra("Medicine",medicine)
            intent.putExtra("Hours",llista)
            intent.putExtra("Dose",dose)
            intent.putExtra("Units",new_units)
            intent.putExtra("Notes",notes)
         */


        button_radiogrupPressed="radioButton_xDay"
        btn_from_to.setOnClickListener {
            select_start_end_dates()
        }
        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                val layout_fromto: LinearLayout =findViewById(R.id.layout_fromto)
                val genderString = resources.getResourceEntryName(radio.id) // "male"
                when(genderString){
                    "radioButton_xDay" ->{
                        button_radiogrupPressed="radioButton_xDay"
                       /* Toast.makeText(applicationContext," On checked change : ${genderString}",
                            Toast.LENGTH_SHORT).show()*/
                        layout_fromto.visibility=View.VISIBLE
                        loadFragment(Pillfrequency_FragmentOne())

                    }
                    "radioButton_eachXDays"->{
                        button_radiogrupPressed="radioButton_eachXDays"
                        /*Toast.makeText(applicationContext," On checked change : ${genderString}",
                            Toast.LENGTH_SHORT).show()*/
                        layout_fromto.visibility=View.VISIBLE
                        loadFragment(Pillfrequency_FragmentTwo())
                    }
                    "radioButton_specificDays"->{
                        button_radiogrupPressed= "radioButton_specificDays"
                        /*Toast.makeText(applicationContext," On checked change : ${genderString}",
                            Toast.LENGTH_SHORT).show()*/
                        layout_fromto.visibility=View.VISIBLE
                        loadFragment(Pillfrequency_FragmentThree())
                    }
                    "radioButton_PuntualDay"->{
                        button_radiogrupPressed= "radioButton_PuntualDay"
                        /*Toast.makeText(applicationContext," On checked change : ${genderString}",
                            Toast.LENGTH_SHORT).show()*/
                        layout_fromto.visibility=View.INVISIBLE
                        loadFragment(Pillfrequency_FragmentFour())
                    }

                    else->{
                        Toast.makeText(applicationContext," On checked change : ${"Error de RadioButton"}",
                            Toast.LENGTH_SHORT).show()
                    }

                }
                
            })
        val image_view = findViewById<ImageButton>(R.id.left_arrow)
        image_view.setOnClickListener {
            onBackPressed()
        }
        btn_accept.setOnClickListener {
            //Quan prenem el boto de acceptar
            val bundle:Bundle? = intent.extras
            val from = bundle?.get("From")
            Log.e("From",from.toString())
            //si vinc de PillMedication
            if(from=="PillMedication"){
                val intent = Intent(this, PillMedication::class.java)
                when(button_radiogrupPressed){
                    "radioButton_xDay" ->{
                        //Log.e("radioButton_xDay","radioButton_xDay SAVED")
                        intent.putExtra("RadioButton",0)
                        intent.putExtra("From",this.ini_day.toString()+"/"+this.ini_month.toString()+"/"+this.ini_year.toString())
                        intent.putExtra("To",this.end_day.toString()+"/"+this.end_month.toString()+"/"+this.end_year.toString())
                    }
                    "radioButton_eachXDays"->{
                        //Log.e("radioButton_eachXDays","radioButton_eachXDays SAVED")
                        intent.putExtra("RadioButton",1)
                        intent.putExtra("RadioButtonValue",new_dose.toString())
                        intent.putExtra("From",this.ini_day.toString()+"/"+this.ini_month.toString()+"/"+this.ini_year.toString())
                        intent.putExtra("To",this.end_day.toString()+"/"+this.end_month.toString()+"/"+this.end_year.toString())
                        //Log.e("dose",new_dose.toString())
                    }
                    "radioButton_specificDays"->{
                        //Log.e("radioButton_specificDays","radioButton_specificDays SAVED")
                        intent.putExtra("RadioButton",2)
                        intent.putExtra("RadioButtonValue",arrayDiesSetmana.toTypedArray())
                        intent.putExtra("From",this.ini_day.toString()+"/"+this.ini_month.toString()+"/"+this.ini_year.toString())
                        intent.putExtra("To",this.end_day.toString()+"/"+this.end_month.toString()+"/"+this.end_year.toString())
                        //Log.e("Week days",arrayDiesSetmana.toString())
                    }
                    "radioButton_PuntualDay"->{
                        //Log.e("radioButton_PuntualDay","radioButton_PuntualDay SAVED")
                        intent.putExtra("RadioButton",3)
                        intent.putExtra("RadioButtonValue",arrayDiesSaltejats.toTypedArray())
                        Log.e("list days",arrayDiesSaltejats.toString())

                    }
                }

                val llista = this.Hours
                intent.putExtra("Medicine",this.Medicine)
                intent.putExtra("Hours",llista.toTypedArray())
                intent.putExtra("Dose",this.DoseIn)
                intent.putExtra("Units",this.Units)
                intent.putExtra("Notes",this.Notes)
                startActivity(intent)
            }
            //Si vinc de Mesurements
            else if (from=="PillMesurements"){
                val intent = Intent(this, Pill_mesurements_info::class.java)
                when(button_radiogrupPressed){
                    "radioButton_xDay" ->{
                        //Log.e("radioButton_xDay","radioButton_xDay SAVED")
                        intent.putExtra("RadioButton",0)
                        intent.putExtra("From",this.ini_day.toString()+"/"+this.ini_month.toString()+"/"+this.ini_year.toString())
                        intent.putExtra("To",this.end_day.toString()+"/"+this.end_month.toString()+"/"+this.end_year.toString())
                    }
                    "radioButton_eachXDays"->{
                        //Log.e("radioButton_eachXDays","radioButton_eachXDays SAVED")
                        intent.putExtra("RadioButton",1)
                        intent.putExtra("RadioButtonValue",new_dose.toString())
                        intent.putExtra("From",this.ini_day.toString()+"/"+this.ini_month.toString()+"/"+this.ini_year.toString())
                        intent.putExtra("To",this.end_day.toString()+"/"+this.end_month.toString()+"/"+this.end_year.toString())
                        //Log.e("dose",new_dose.toString())
                    }
                    "radioButton_specificDays"->{
                        //Log.e("radioButton_specificDays","radioButton_specificDays SAVED")
                        intent.putExtra("RadioButton",2)
                        intent.putExtra("RadioButtonValue",arrayDiesSetmana.toTypedArray())
                        intent.putExtra("From",this.ini_day.toString()+"/"+this.ini_month.toString()+"/"+this.ini_year.toString())
                        intent.putExtra("To",this.end_day.toString()+"/"+this.end_month.toString()+"/"+this.end_year.toString())
                        //Log.e("Week days",arrayDiesSetmana.toString())
                    }
                    "radioButton_PuntualDay"->{
                        //Log.e("radioButton_PuntualDay","radioButton_PuntualDay SAVED")
                        intent.putExtra("RadioButton",3)
                        intent.putExtra("RadioButtonValue",arrayDiesSaltejats.toTypedArray())
                        Log.e("list days",arrayDiesSaltejats.toString())

                    }
                }
                val TitolMesurement = bundle.get("TitolMesurement")
                val UnitatsMesurement = bundle.get("UnitatsMesurement")
                val llista = this.Hours
                intent.putExtra("Medicine",this.Medicine)
                intent.putExtra("Hours",llista.toTypedArray())
                intent.putExtra("Dose",this.DoseIn)
                intent.putExtra("Units",this.Units)
                intent.putExtra("Notes",this.Notes)
                intent.putExtra("TitolMesurement",TitolMesurement as String)
                intent.putExtra("UnitatsMesurement",UnitatsMesurement as String)
                startActivity(intent)
            }
            //Si vinc de PillSports
            else if (from=="PillSports"){
                val intent = Intent(this, PillSports::class.java)
                when(button_radiogrupPressed){
                    "radioButton_xDay" ->{
                        //Log.e("radioButton_xDay","radioButton_xDay SAVED")
                        intent.putExtra("RadioButton",0)
                        intent.putExtra("From",this.ini_day.toString()+"/"+this.ini_month.toString()+"/"+this.ini_year.toString())
                        intent.putExtra("To",this.end_day.toString()+"/"+this.end_month.toString()+"/"+this.end_year.toString())
                    }
                    "radioButton_eachXDays"->{
                        //Log.e("radioButton_eachXDays","radioButton_eachXDays SAVED")
                        intent.putExtra("RadioButton",1)
                        intent.putExtra("RadioButtonValue",new_dose.toString())
                        intent.putExtra("From",this.ini_day.toString()+"/"+this.ini_month.toString()+"/"+this.ini_year.toString())
                        intent.putExtra("To",this.end_day.toString()+"/"+this.end_month.toString()+"/"+this.end_year.toString())
                        //Log.e("dose",new_dose.toString())
                    }
                    "radioButton_specificDays"->{
                        //Log.e("radioButton_specificDays","radioButton_specificDays SAVED")
                        intent.putExtra("RadioButton",2)
                        intent.putExtra("RadioButtonValue",arrayDiesSetmana.toTypedArray())
                        intent.putExtra("From",this.ini_day.toString()+"/"+this.ini_month.toString()+"/"+this.ini_year.toString())
                        intent.putExtra("To",this.end_day.toString()+"/"+this.end_month.toString()+"/"+this.end_year.toString())
                        //Log.e("Week days",arrayDiesSetmana.toString())
                    }
                    "radioButton_PuntualDay"->{
                        //Log.e("radioButton_PuntualDay","radioButton_PuntualDay SAVED")
                        intent.putExtra("RadioButton",3)
                        intent.putExtra("RadioButtonValue",arrayDiesSaltejats.toTypedArray())
                        Log.e("list days",arrayDiesSaltejats.toString())

                    }
                }
                val llista = this.Hours
                val Activity = bundle.get("Activity")
                intent.putExtra("Medicine",this.Medicine)
                intent.putExtra("Hours",llista.toTypedArray())
                intent.putExtra("Dose",this.DoseIn)
                intent.putExtra("Units",this.Units)
                intent.putExtra("Notes",this.Notes)
                intent.putExtra("Activity",Activity as String)
                startActivity(intent)
            }

            /*
            Log.e("ini_day",ini_day.toString())
            Log.e("ini_month",ini_month.toString())
            Log.e("ini_year",ini_year.toString())
            Log.e("end_day",end_day.toString())
            Log.e("end_month",end_month.toString())
            Log.e("end_year",end_year.toString())
            */
        }
    }

    private fun loadFragment(fragment:Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentFrequency, fragment)
        fragmentTransaction.commit()
    }
    /*
    fun radio_button_click(view: View){
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(radio_group.checkedRadioButtonId)
        Toast.makeText(applicationContext,"On click : ${radio.text}",
            Toast.LENGTH_SHORT).show()
    }*/

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
            val i = set_ok_date(new_ini_day,new_ini_month,new_ini_year,new_end_day,new_end_month,new_end_year)
            if(i == 0){
                mAlertDialog.dismiss()
            }
            else{
                Toast.makeText(applicationContext,"Invalid date", Toast.LENGTH_SHORT).show()
            }
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
    }

    //al clicar ok
    fun set_ok_date(ini_day:Int,ini_month:Int,ini_year:Int,end_day:Int,end_month:Int,end_year:Int): Int {
        this.end_day=end_day
        this.end_month=end_month
        this.end_year=end_year

        var data_ini=this.ini_day.toString()+"/"+this.ini_month.toString()+"/"+this.ini_year.toString()

        this.ini_day=ini_day
        this.ini_month=ini_month
        this.ini_year=ini_year

        var data_end=this.end_day.toString()+"/"+this.end_month.toString()+"/"+this.end_year.toString()
        val dateStart = LocalDate.parse(data_ini, DateTimeFormatter.ofPattern("d/M/y"))
        val dateEnd = LocalDate.parse(data_end, DateTimeFormatter.ofPattern("d/M/y"))
        if(dateStart<=dateEnd){
            btn_from_to.text = data_ini+" to "+data_end
            return 0
        }
        else{
            return 1
        }
    }
    fun setDose(dose :Int){
        new_dose = dose
    }
    fun getDiesSetmana(): MutableList<String> {
        return arrayDiesSetmana
    }
    fun setDiesSetmana(week : MutableList<String>){
        arrayDiesSetmana = week
    }
    fun getDiesSaltejats(): MutableList<String> {
        return arrayDiesSaltejats
    }
    fun setDiesSaltejats(week : MutableList<String>){
        arrayDiesSaltejats = week
    }

}
