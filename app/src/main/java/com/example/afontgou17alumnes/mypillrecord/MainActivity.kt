package com.example.afontgou17alumnes.mypillrecord

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.ui.Pill.Pill_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.Calendar_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.settings.ajustes_activity
import com.example.afontgou17alumnes.mypillrecord.ui.statistics.Statistics_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.team.Team_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.today.Today_Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {
    var contador=0
    var primer=0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{
       item->
       when(item.itemId){
           R.id.action_Today->{
               contador=contador+1
               replaceFragment(Today_Fragment())
               Toast.makeText(this,"Today",Toast.LENGTH_SHORT).show()
               toolbar.title = "TODAY"
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_calendar->{

               replaceFragment(Calendar_fragment())
               //FragmentManager.popBackStack("calendar",)

               Toast.makeText(this,"Calendar",Toast.LENGTH_SHORT).show()
               toolbar.title ="CALENDAR"
               /*if(primer==0){
                   replaceFragment(Calendar_fragment())
                   primer=1
                   contador=0
               }else{
               while(contador>0){
                   contador= contador-1
                   getFragmentManager().popBackStack()
               }}*/
               return@OnNavigationItemSelectedListener true
               //val new_intent = Intent(this, TempActivity::class.java)
               //startActivity(new_intent)
           }
           R.id.action_team->{
               contador=contador+1
               replaceFragment(Team_fragment())
               Toast.makeText(this,"Team",Toast.LENGTH_SHORT).show()
               toolbar.title ="TEAM"
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_pills->{
               contador=contador+1
               replaceFragment(Pill_fragment())
               Toast.makeText(this,"Add",Toast.LENGTH_SHORT).show()
               toolbar.title ="THERAPY"
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_statistcs-> {
               contador=contador+1
               replaceFragment(Statistics_fragment())
               Toast.makeText(this,"Statistics",Toast.LENGTH_SHORT).show()
               toolbar.title ="STATISTICS"
               return@OnNavigationItemSelectedListener true
           }
       }
       false

   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(Today_Fragment())


    }
    private fun replaceFragment(fragment: Fragment){

        val fragmentTransaction  = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id==R.id.options_item){
            Toast.makeText(this,"opcions",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ajustes_activity::class.java);
            startActivity(intent);
        }
        if(id== R.id.pdf_item){
            Toast.makeText(this,"PDF is being generated",Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
