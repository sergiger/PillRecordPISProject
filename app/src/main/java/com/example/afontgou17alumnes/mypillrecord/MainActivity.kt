package com.example.afontgou17alumnes.mypillrecord

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.notifications.NotificationUtils
import com.example.afontgou17alumnes.mypillrecord.ui.Pill.Pill_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.Calendar_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.settings.ajustes_activity
import com.example.afontgou17alumnes.mypillrecord.ui.statistics.Statistics_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.today.TodayFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_main.*
import com.itextpdf.text.Document
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    private val STORAGE_CODE: Int = 100;
    var currentFragment=0
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{
       item->
       when(item.itemId){
           R.id.action_Today->{
               if(currentFragment!=0){
                   replaceFragment(TodayFragment())
                   Toast.makeText(this,"Today",Toast.LENGTH_SHORT).show()
                   toolbar.title = "TODAY"
                   currentFragment=0
               }
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_calendar->{
               if(currentFragment!=1){
                   replaceFragment(Calendar_fragment())
                   Toast.makeText(this,"Calendar",Toast.LENGTH_SHORT).show()
                   toolbar.title ="CALENDAR"
                   currentFragment=1
               }
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_team->{
               Toast.makeText(this,"Team (In next version",Toast.LENGTH_SHORT).show()
               /*replaceFragment(Team_fragment())

               toolbar.title ="TEAM"*/
               //currentFragment=2
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_pills->{
               if(currentFragment!=3){
                   replaceFragment(Pill_fragment())
                   Toast.makeText(this,"Add",Toast.LENGTH_SHORT).show()
                   toolbar.title ="THERAPY"
                   currentFragment=3
               }
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_statistcs-> {
               if(currentFragment!=4) {
                   if (Controller.ja_iniciat == false) {
                       replaceFragment(Statistics_fragment())
                       Controller.ja_iniciat = true
                   } else {
                       go_To_Statistics(0)
                   }
                   Toast.makeText(this, "Statistics", Toast.LENGTH_SHORT).show()
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
        Controller.setRemindersData()
        generarNextNotification()

    }
    fun generarNextNotification(){
        if (Controller.user.areThereReminders()){
            val mNotificationTime = Controller.user.getNextReminder().getMilisFromNow() //Set after 5 seconds
            NotificationUtils().setNotification(mNotificationTime, this@MainActivity)
        }
        else{
            Toast.makeText(this,"There are no reminders",Toast.LENGTH_LONG).show()
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction  = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if(id == R.id.options_item){
            Toast.makeText(this,"opcions",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ajustes_activity::class.java)
            startActivity(intent)
        }
        if(id == R.id.pdf_item){
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
        val mDoc = Document()
        //pdf file name
        //val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        val mFileName = "MyPillrecord:"+LocalDate.now().toString()
        //pdf file path (si volem crear una carpeta o algo s'ha de fer des d'aquí escribint la direcció exacta on volem que es crei el document
        val mFilePath = Environment.getExternalStorageDirectory().toString() +"/" + mFileName +".pdf"
        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            //open the document for writing
            mDoc.open()

            //get text from EditText i.e. textEt
            var mText = "Holaa"
            if(currentFragment==0){//Today
                mText="Toady Reminders:\n\n"
                for(reminder in Controller.user.getTodayReminders()){
                    mText+=reminder.toStringPDF()
                }
            }
            else if(currentFragment==3){//Therapies
                mText="Current Therapies:\n\n"
                for(terapia in Controller.user.therapies){
                    if(terapia.frequency.isActive())
                        mText+=terapia.toStringPDF()
                }
            }
            else if(currentFragment==4){//Statistics
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
            }


            //Afegir l'autor del document
            mDoc.addAuthor("MY PILL RECORD")

            //Afegir tot el que volem posar al cos del document en ordre
            mDoc.add(Paragraph(mText))

            //Tancar el document i guardar-lo
            mDoc.close()

            //Mostrar el camí al document, perquè sinó pot costar molt de trobar-lo
            Toast.makeText(this, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_LONG).show()
            val path=Environment.getExternalStorageDirectory().toString() +"/" + mFileName
            //openFile(path.toUri()) // No m'en surto de obrir els documents, per tant, ara per ara simplement guardarem els documents
        }
        catch (e: Exception){
            //Si quelcom va malament saltarà aquest error
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
    val PICK_PDF_FILE = 2

    fun openFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }

        startActivityForResult(intent, PICK_PDF_FILE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Ja tenim permisos per utilitzar el sotrage i per tant podem generar i guardar el PDF
                    savePdf()
                }
                else{
                    //Permisos denegats per accedir al storage del mobil
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
