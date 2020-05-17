package com.example.afontgou17alumnes.mypillrecord.ui.team

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.afontgou17alumnes.mypillrecord.R

class team_follower_page : AppCompatActivity() {
    var follower = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_follower_page)
        val bundle:Bundle? = intent.extras
        val follower = bundle?.get("follower")
        if(follower!= null){
            this.follower= follower as String
        }
        val infoTextView : TextView = findViewById(R.id.tw_Follower_user)
        infoTextView.text= "  ${follower as CharSequence?}"

        val image_view = findViewById<ImageButton>(R.id.left_arrow)
        image_view.setOnClickListener {
            onBackPressed()
        }
    }

}
