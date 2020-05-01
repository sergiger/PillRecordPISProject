package com.example.afontgou17alumnes.mypillrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
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
}
