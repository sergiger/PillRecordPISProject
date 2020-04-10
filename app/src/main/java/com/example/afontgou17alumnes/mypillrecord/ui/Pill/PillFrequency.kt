package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_pill_frequency.*
import kotlinx.android.synthetic.main.share_team_activity.*
import kotlinx.android.synthetic.main.share_team_activity.radio_group

class PillFrequency : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_frequency)
        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                val genderString = resources.getResourceEntryName(radio.id) // "male"
                when(genderString){
                    "radioButton_xDay" ->{
                        Toast.makeText(applicationContext," On checked change : ${genderString}",
                            Toast.LENGTH_SHORT).show()
                        loadFragment(Pillfrequency_FragmentOne())

                    }
                    "radioButton_eachXDays"->{
                        Toast.makeText(applicationContext," On checked change : ${genderString}",
                            Toast.LENGTH_SHORT).show()
                        loadFragment(Pillfrequency_FragmentTwo())
                    }
                    "radioButton_specificDays"->{
                        Toast.makeText(applicationContext," On checked change : ${genderString}",
                            Toast.LENGTH_SHORT).show()
                        loadFragment(Pillfrequency_FragmentThree())
                    }
                    "radioButton_PuntualDay"->{
                        Toast.makeText(applicationContext," On checked change : ${genderString}",
                            Toast.LENGTH_SHORT).show()
                        loadFragment(Pillfrequency_FragmentFour())
                    }

                    else->{
                        Toast.makeText(applicationContext," On checked change : ${"Error de RadioButton"}",
                            Toast.LENGTH_SHORT).show()
                    }

                }
                
            })
        val image_view = findViewById(R.id.left_arrow) as ImageButton
        image_view.setOnClickListener {
            onBackPressed()
        }
        btn_accept.setOnClickListener {
            onBackPressed()
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
}
