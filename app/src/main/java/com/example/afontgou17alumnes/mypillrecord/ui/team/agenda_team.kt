package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageButton
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.agenda_team_activity.*

class agenda_team : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agenda_team_activity)

        val image_view = findViewById(R.id.left_arrow) as ImageButton
        image_view.setOnClickListener {
            onBackPressed()
        }

        val persones: ArrayList<String> = ArrayList()
        addPersones(persones)

        rv_contactes_list.layoutManager = LinearLayoutManager(this)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        rv_contactes_list.layoutManager = GridLayoutManager(this, 1)

        // Access the RecyclerView Adapter and load the data into it
        rv_contactes_list.adapter = personAdapter(persones, this)

    }

    fun addPersones(persones:ArrayList<String> = ArrayList()){
        persones.add("Alex")
        persones.add("Sergi")
        persones.add("Joan")
        persones.add("Arthur")
        persones.add("pep")
        persones.add("victor")
        persones.add("manel")
        persones.add("alsa")
        persones.add("willy")
        persones.add("gumersindo")
        persones.add("vichy")
        persones.add("natalia")
    }
}
