package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_pill_frequency.*
import kotlinx.android.synthetic.main.share_team_activity.radio_group

class PillFrequency : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_frequency)
        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext," On checked change : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
            })
        val image_view = findViewById<ImageButton>(R.id.left_arrow)
        image_view.setOnClickListener {
            onBackPressed()
        }
        btn_accept.setOnClickListener {
            onBackPressed()
        }
    }
    fun radio_button_click(view: View){
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(radio_group.checkedRadioButtonId)
        Toast.makeText(applicationContext,"On click : ${radio.text}",
            Toast.LENGTH_SHORT).show()
    }
}
