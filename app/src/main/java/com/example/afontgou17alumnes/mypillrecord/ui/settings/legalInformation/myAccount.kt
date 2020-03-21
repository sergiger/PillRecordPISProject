package com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.ui.login.LoginActivity
import kotlinx.android.synthetic.main.ajustes_activity.*
import kotlinx.android.synthetic.main.legal_main_activity.*

class myAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_account_activity)

        back_icon.setOnClickListener {
            onBackPressed()
        }

        val arrayAdapter: ArrayAdapter<*>
        val users = arrayOf("Change Pasword", "Close Session",
            "Extra information","Gender","Year of birth", "Height","weight")

        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.opcions_menu)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, users)
        mListView.adapter = arrayAdapter
        opcions_menu.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            onSelectedMenu(position)
        }
    }
    private fun onSelectedMenu(position: Int){
        if(position==0){//Change Pasword
            Toast.makeText(this,"change pasword", Toast.LENGTH_SHORT).show()
        }
        else if(position==1){//Close Session
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent);
            Toast.makeText(this,"sesion closed", Toast.LENGTH_SHORT).show()
        }
        else if(position==2){//Extra information
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent);
            //Aqui s'haurà de posar que el user i la contrassenya son null, per a que no puguis tornar
            //a entrar a la conta amb el mateix user
        }
        else if(position==3){//Gender

        }
        else if(position==4){//Year of birth
            Toast.makeText(this,"Ueeeep, piloooootes!!!", Toast.LENGTH_SHORT).show()
        }
        else if(position==4){//Height
            //Toast.makeText(this,"Ueeep", Toast.LENGTH_SHORT).show()
        }
        else if(position==4){//weight

        }
    }
}
