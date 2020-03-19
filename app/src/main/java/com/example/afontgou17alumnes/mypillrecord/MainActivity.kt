package com.example.afontgou17alumnes.mypillrecord

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.ImageView
import android.widget.Toast
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

        navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(Today_Fragment())

        btnProvetes.setOnClickListener {
            Toast.makeText(this, "Primera prova", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, activity_Register4::class.java)
            startActivity(intent);
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction  = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}
