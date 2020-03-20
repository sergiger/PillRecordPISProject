package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_main.*

class Pill_fragment : Fragment() {

    companion object {
        fun newInstance() =
            Pill_fragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pill_fragment_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
    fun afegirTherapy(view: View) {

        //val intent = Intent(this,pillPlanificar_fragment::class.java)
        //startActivity(intent)
    }

}
