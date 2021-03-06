package com.example.afontgou17alumnes.mypillrecord

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.ui.Pill.Pill_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.Calendar_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.settings.ajustes_activity
import com.example.afontgou17alumnes.mypillrecord.ui.statistics.Statistics_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.team.Team_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.team.noConnectionShareTeam
import com.example.afontgou17alumnes.mypillrecord.ui.today.TodayFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.inappmessaging.display.internal.layout.util.BackButtonHandler
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.from_to_dialogue_pdf.view.*
import kotlinx.android.synthetic.main.year_of_birth_dialoge.view.OK
import java.io.FileOutputStream
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private val STORAGE_CODE: Int = 100;
    var currentFragment=-1
    var hosts= arrayOf("joanorteu99@gmail.com","alendinez4@gmail.com","sergiger00@gmail.com")
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{
       item->
       when(item.itemId){
           R.id.action_Today->{
               if(Controller.main_activity_fragment!=0){
                   findViewById<ImageView>(R.id.pdf_item).isVisible=true
                   replaceFragment(TodayFragment())
                   toolbar.title = "TODAY"
                   currentFragment=0
                   //generarNextNotification()
               }
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_calendar->{
               if(Controller.main_activity_fragment!=1){
                   findViewById<ImageView>(R.id.pdf_item).isVisible=true
                   replaceFragment(Calendar_fragment())
                   toolbar.title ="CALENDAR"
                   currentFragment=1
               }
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_team->{
               if(Controller.main_activity_fragment!=3) {
                   findViewById<ImageView>(R.id.pdf_item).isVisible=false
                   Controller.internet(this)
                   if (Controller.connected) {
                       replaceFragment(Team_fragment())
                   } else {
                       replaceFragment(noConnectionShareTeam())
                   }
                   toolbar.title = "TEAM"
                   currentFragment = 2
               }
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_pills->{
               if(Controller.main_activity_fragment!=4){
                   findViewById<ImageView>(R.id.pdf_item).isVisible=true
                   replaceFragment(Pill_fragment())
                   toolbar.title ="THERAPY"
                   currentFragment=3
               }
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_statistcs-> {
               if(Controller.main_activity_fragment!=2) {
                   findViewById<ImageView>(R.id.pdf_item).isVisible=true
                   if (Controller.ja_iniciat == false) {
                       replaceFragment(Statistics_fragment())
                       Controller.ja_iniciat = true
                   } else {
                       go_To_Statistics(0)
                   }
                   toolbar.title = "STATISTICS"
                   currentFragment = 4
               }
               return@OnNavigationItemSelectedListener true
           }
       }
       false

   }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val bundle:Bundle? = intent.extras
        //medicine
        val goTo = bundle?.get("goTo")
        if(goTo!= null){
            if(goTo == "Pill"){
                replaceFragment(Pill_fragment())
            }
        }
        else{
            replaceFragment(TodayFragment())
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction  = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(this.hosts.contains(Controller.user.email))
            menuInflater.inflate(R.menu.toolbar_menu_host, menu)
        else
            menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id == R.id.options_item){
            val intent = Intent(this, ajustes_activity::class.java)
            startActivity(intent)
        }
        if(id == R.id.pdf_item){
            if(Controller.main_activity_fragment!=3)
                Toast.makeText(this,"PDF is being generated",Toast.LENGTH_LONG).show()
            //we need to handle runtime permission for devices with marshmallow and above
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                //system OS >= Marshmallow(6.0), check permission is enabled or not
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    //permission was not granted, request it
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, STORAGE_CODE)
                }
                else{
                    //permission already granted, call savePdf() method
                    savePdf()
                }
            }
            else{
                //system OS < marshmallow, call savePdf() method
                savePdf()
            }
        }
        if(id == R.id.eliminarRemindersITherapys){
            Controller.clearRemindersAndTherapysFromFirebaseAndShare()
        }
        return super.onOptionsItemSelected(item)
    }
    fun go_To_Statistics(id:Int){

        val bundle = Bundle()
        bundle.putInt("start",id)

        val transaction = this.supportFragmentManager.beginTransaction()
        val statisticsFragment = Statistics_fragment()
        statisticsFragment.arguments = bundle

        transaction.replace(R.id.fragmentContainer, statisticsFragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed(): Unit {
    }
    private fun savePdf() {
        //create object of Document class
        try {
            //get text from EditText i.e. textEt
            var mText = "Holaa"
            var mFileName = "MyPillrecord:"+LocalDate.now().toString()
            if(Controller.main_activity_fragment==0){//Today
                mText="Toady Reminders:\n\n"
                mFileName="MyPillRecord_Today:"+LocalDate.now().toString()
                for(reminder in Controller.user.getTodayReminders()){
                    mText+=reminder.toStringPDF()
                }
                printPDF(mText,mFileName)
            }
            else if(Controller.main_activity_fragment==1){//Estem a Calendar
                mFileName="MyPillRecord_Calendar:"+LocalDate.now().toString()
                //Inflate the dialog with custom view
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.from_to_dialogue_pdf, null)
                //AlertDialogBuilder
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Create PDF with reminders between:")
                //show dialog
                val  mAlertDialog = mBuilder.show()
                //login button click of custom layout
                mDialogView.OK.setOnClickListener {
                    //get text from EditTexts of custom layout
                    val from = Controller.getLocalDate(mDialogView.input_from.year,
                                                            mDialogView.input_from.month+1,
                                                            mDialogView.input_from.dayOfMonth)
                    val to =Controller.getLocalDate(mDialogView.input_to.year,
                        mDialogView.input_to.month+1,
                        mDialogView.input_to.dayOfMonth)
                    mText="Resume of all reminders from ${from.toString()} to ${to.toString()}:\n\n"
                    for(reminder in Controller.user.getReminerBetween(from,to)){
                        mText+=reminder.toStringPDF_calendar()
                    }
                    //dismiss dialog
                    printPDF(mText,mFileName)
                    mAlertDialog.dismiss()

                }
                //cancel button click of custom layout
                mDialogView.cancell.setOnClickListener {
                    Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show()
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }

            }
            else if(Controller.main_activity_fragment==4){//Therapies
                mFileName="MyPillRecord_ActiveTherapies:"+LocalDate.now().toString()
                mText="Current Therapies:\n\n"
                for(terapia in Controller.user.therapies){
                    if(terapia.frequency.isActive())
                        mText+=terapia.toStringPDF()
                }
                printPDF(mText,mFileName)
            }
            else if(Controller.main_activity_fragment==2){//Statistics
                mFileName="MyPillRecord_Statistics:"+LocalDate.now().toString()
                mText="Statistics:\n"
                var cont=0
                mText+="\n     Temperature:"
                for(valor in Controller.user.statistics.temperatureData){
                    var valorString=valor.value.toString()+", "
                    if(cont==Controller.user.statistics.temperatureData.size-1)
                        valorString=valor.value.toString()
                    if(cont%8!=0){
                        mText+=valorString
                    }
                    else{
                        mText+="\n         "+valorString
                    }
                    cont++
                }
                cont=0
                mText+="\n     Weight:"
                for(valor in Controller.user.statistics.weightData){
                    var valorString=valor.value.toString()+", "
                    if(cont==Controller.user.statistics.weightData.size-1)
                        valorString=valor.value.toString()
                    if(cont%8!=0)
                        mText+=valorString
                    else
                        mText+="\n         "+valorString
                    cont++
                }
                cont=0
                mText+="\n     HeartRate:"
                for(valor in Controller.user.statistics.heartRateData){
                    var valorString=valor.value.toString()+", "
                    if(cont==Controller.user.statistics.heartRateData.size-1)
                        valorString=valor.value.toString()
                    if(cont%8!=0)
                        mText+=valorString
                    else
                        mText+="\n         "+valorString
                    cont++
                }
                cont=0
                mText+="\n     Glucose Before Eating:"
                for(valor in Controller.user.statistics.glucoseBeforeData){
                    var valorString=valor.value.toString()+", "
                    if(cont==Controller.user.statistics.glucoseBeforeData.size-1)
                        valorString=valor.value.toString()
                    if(cont%8!=0)
                        mText+=valorString
                    else
                        mText+="\n         "+valorString
                    cont++
                }
                cont=0
                mText+="\n     Glucose After Eating:"
                for(valor in Controller.user.statistics.glucoseAfterData){
                    var valorString=valor.value.toString()+", "
                    if(cont==Controller.user.statistics.glucoseAfterData.size-1)
                        valorString=valor.value.toString()
                    if(cont%8!=0)
                        mText+=valorString
                    else
                        mText+="\n         "+valorString
                    cont++
                }
                cont=0
                mText+="\n     Arterial Preassure:"
                for(valor in Controller.user.statistics.arterialPressureData){
                    var valorString=valor.value.toString()+", "
                    if(cont==Controller.user.statistics.arterialPressureData.size-1)
                        valorString=valor.value.toString()
                    if(cont%8!=0)
                        mText+=valorString
                    else
                        mText+="\n         "+valorString
                    cont++
                }
                printPDF(mText,mFileName)
            }
        }
        catch (e: Exception){
            //Si quelcom va malament saltarà aquest error
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun printPDF(mText:String,mFileName:String){
        val mDoc = Document()
        //pdf file path (si volem crear una carpeta o algo s'ha de fer des d'aquí escribint la direcció exacta on volem que es crei el document
        val mFilePath = Environment.getExternalStorageDirectory().toString() +"/" + mFileName +".pdf"

        //create instance of PdfWriter class
        PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

        //open the document for writing
        mDoc.open()

        //Afegir l'autor del document
        mDoc.addAuthor("MY PILL RECORD")

        //Afegir tot el que volem posar al cos del document en ordre
        mDoc.add(Paragraph(mText))

        //Tancar el document i guardar-lo
        mDoc.close()

        //Mostrar el camí al document, perquè sinó pot costar molt de trobar-lo
        Toast.makeText(this, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_LONG)
            .show()
        //val path = Environment.getRootDirectory().toString() + "/" + mFileName
        //openFile(Uri.parse(mFilePath)) // No m'en surto de obrir els documents, per tant, ara per ara simplement guardarem els documents
    }

    val PICK_PDF_FILE = 2

    fun openFile(pickerInitialUri: Uri) {
        /*val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }

        startActivityForResult(intent, PICK_PDF_FILE)
        */
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, pickerInitialUri)
        intent.setDataAndType(pickerInitialUri, "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
        startActivity(intent)
    }


}
