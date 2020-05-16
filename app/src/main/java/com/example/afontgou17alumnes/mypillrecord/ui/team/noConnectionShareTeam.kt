package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.team_fragment_fragment.*

class noConnectionShareTeam : Fragment() {

    companion object {
        fun newInstance() =
            noConnectionShareTeam()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_team_noconnection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel

    }

}
