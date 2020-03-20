package com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toolbar
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.ui.login.LoginActivity
import kotlinx.android.synthetic.main.ajustes_activity.*
import kotlinx.android.synthetic.main.legal_main_activity.*


class legal_main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.legal_main_activity)

        back_legal_icon.setOnClickListener {
            onBackPressed()
        }

        val users = arrayOf("About smartpatients","User recuirements", "Privacy policy")

        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.opcions_legal_menu)
        val arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, users)
        mListView.adapter = arrayAdapter
        opcions_legal_menu.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            onSelectedMenu(position)
        }
    }

    private fun onSelectedMenu(position: Int){
        if(position==0){//About SmartPatients
            val intent = Intent(this, smartPatients::class.java);
            startActivity(intent);
        }
        else if(position==1){//User requirements
            val intent = Intent(this, userRequirements::class.java);
            startActivity(intent);
        }
        else if(position==2){//Privacy policy
            val intent = Intent(this, privacy::class.java);
            startActivity(intent);
        }
    }

}
