package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.ui.Pill.PillHourListAdapter2
import kotlinx.android.synthetic.main.agenda_team_activity.*

class agenda_team : AppCompatActivity() {
    var timeListViewfrequency : ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agenda_team_activity)

        val image_view = findViewById<ImageButton>(R.id.left_arrow)
        image_view.setOnClickListener {
            onBackPressed()
        }

        //Controller.getFromFirebaseshareData()
        listHasChanged(Controller.IShareTo.keys.toMutableList())
    }

    fun listHasChanged( llista: MutableList<String>){
        val arraylocal = llista.toTypedArray()
        val listsharecontacts: ListView? = findViewById(R.id.rv_contactes_list)
        val timeAdapter: personAdapter = personAdapter(this, llista.toTypedArray(),this)
        if (listsharecontacts != null) {
            listsharecontacts.adapter = timeAdapter
        }
        timeListViewfrequency = listsharecontacts
        Log.e("timeListViewfrequency", timeListViewfrequency.toString())
        if(llista.size > 1){
            Toast.makeText(this, "Added", Toast.LENGTH_LONG).show()
        }
        timeListViewfrequency?.setOnItemClickListener { adapterView, view, i, l ->
            Log.e("POSITION", timeAdapter?.getItem(i).toString())
            //var text : TextView = view.findViewById(R.id.tw_hour)
        }
    }
}
