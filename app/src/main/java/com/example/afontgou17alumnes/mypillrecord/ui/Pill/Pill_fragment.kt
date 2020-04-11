package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
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
            val intent = Intent(activity,Pillplanificar::class.java)
            startActivity(intent)
        }
    }
}
