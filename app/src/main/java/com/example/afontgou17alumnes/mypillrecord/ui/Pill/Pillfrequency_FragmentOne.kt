package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.fragment_pillfrequency__one.*


/**
 * A simple [Fragment] subclass.
 */
class Pillfrequency_FragmentOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pillfrequency__one, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_add_field_button.setOnClickListener {

        }
    }
    /*fun onAddField(v: View?) {
        val inflater =
            getSystemService<Any>(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val rowView: View = inflater.inflate(R.layout.field, null)
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1)
    }

    fun onDelete(v: View) {
        parentLinearLayout.removeView(v.parent as View)
    }*/
}
