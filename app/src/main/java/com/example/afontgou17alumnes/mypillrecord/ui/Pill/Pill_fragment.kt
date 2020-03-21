package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pill_fragment_fragment.*

class Pill_fragment : Fragment() {

    companion object {
        fun newInstance() =
            Pill_fragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Button btn1 = (Button) view.findViewById()
        return inflater.inflate(R.layout.pill_fragment_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_add_therapy.setOnClickListener{
            val intent = Intent(getActivity(),Pillplanificar::class.java)
            startActivity(intent)
        }
    }
}
