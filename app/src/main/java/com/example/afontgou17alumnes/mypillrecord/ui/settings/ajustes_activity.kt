package com.example.afontgou17alumnes.mypillrecord.ui.settings

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.ui.login.LoginActivity
import com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation.legal_main
import com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation.myAccount
import kotlinx.android.synthetic.main.ajustes_activity.*
import java.text.FieldPosition

class ajustes_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ajustes_activity)

        val image_view = findViewById(R.id.back_icon) as ImageView
        image_view.setOnClickListener {
            onBackPressed()
        }
        val arrayAdapter: ArrayAdapter<*>
        val users = arrayOf("My Account", "Change Notifications", "Close Session",
            "Legal information","Version B.34.53")

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
        if(position==0){//My account
            val intent = Intent(this, myAccount::class.java);
            startActivity(intent);
            Toast.makeText(this,"myAccount",Toast.LENGTH_SHORT).show()
        }
        else if(position==1){//Change Notificatiions
            Toast.makeText(this,"Soon",Toast.LENGTH_SHORT).show()
        }
        else if(position==2){//close Session
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent);
            //Aqui s'haurà de posar que el user i la contrassenya son null, per a que no puguis tornar
            //a entrar a la conta amb el mateix user
        }
        else if(position==3){//Legal information
            val intent = Intent(this, legal_main::class.java);
            startActivity(intent);
        }
        else if(position==4){//versió, aquí no sé que posar-hi ara
            Toast.makeText(this,"Ueeeep, piloooootes!!!",Toast.LENGTH_SHORT).show()
        }
    }
}