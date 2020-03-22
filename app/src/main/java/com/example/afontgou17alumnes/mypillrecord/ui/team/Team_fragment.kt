package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.ui.settings.legalInformation.myAccount
import kotlinx.android.synthetic.main.team_fragment_fragment.*

class Team_fragment : Fragment() {

    companion object {
        fun newInstance() =
            Team_fragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_fragment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel

        btn_share.setOnClickListener {
            val intent = Intent(activity, share_team::class.java);
            startActivity(intent);
            Toast.makeText(activity,"Share With", Toast.LENGTH_SHORT).show()
        }
        btn_agenda.setOnClickListener {
            val intent = Intent(activity, agenda_team::class.java);
            startActivity(intent);
            Toast.makeText(activity,"Agenda", Toast.LENGTH_SHORT).show()
        }
    }

}
