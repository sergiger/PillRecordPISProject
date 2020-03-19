package com.example.afontgou17alumnes.mypillrecord

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.Menu
import com.example.afontgou17alumnes.mypillrecord.Pill.Pill_fragment
import com.example.afontgou17alumnes.mypillrecord.calendar.Calendar_fragment
import com.example.afontgou17alumnes.mypillrecord.statistics.Statistics_fragment
import com.example.afontgou17alumnes.mypillrecord.team.Team_fragment
import com.example.afontgou17alumnes.mypillrecord.today.Today_Fragment
import com.example.afontgou17alumnes.mypillrecord.ui.login.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{
       item->
       when(item.itemId){
           R.id.action_Today->{
               replaceFragment(Today_Fragment())
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_calendar->{
               replaceFragment(Calendar_fragment())
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_team->{
               replaceFragment(Team_fragment())
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_pills->{
               replaceFragment(Pill_fragment())
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_statistcs-> {
               replaceFragment(Statistics_fragment())
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
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }
}
