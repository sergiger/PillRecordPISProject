package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.activity_add_unplanned_medicine.*
import kotlinx.android.synthetic.main.fragment_pillfrequency__three.*

/**
 * A simple [Fragment] subclass.
 */
class Pillfrequency_FragmentThree : Fragment() {
    //var week= mutableListOf<String>("0","0","0","0","0","0","0")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pillfrequency__three, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cb_monday.setOnClickListener{
            val week = (activity as PillFrequency?)?.getDiesSetmana()
            if(week!![0]=="0"){
                week[0]="1"
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
            else{
                week[0]="0"
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
        }
        cb_tuesday.setOnClickListener {
            val week = (activity as PillFrequency?)?.getDiesSetmana()
            if(week!![1]=="0"){
                week[1]="1"
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
            else{
                week[1]="0"
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
        }
        cb_wednesday.setOnClickListener {
            val week = (activity as PillFrequency?)?.getDiesSetmana()
            if(week!![2]=="0"){
                week.set(2,"1")
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
            else{
                week.set(2,"0")
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
        }
        cb_thursday.setOnClickListener {
            val week = (activity as PillFrequency?)?.getDiesSetmana()
            if(week!![3]=="0"){
                week.set(3,"1")
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
            else{
                week.set(3,"0")
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
        }
        cb_friday.setOnClickListener {
            val week = (activity as PillFrequency?)?.getDiesSetmana()
            if(week!![4]=="0"){
                week.set(4,"1")
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
            else{
                week.set(4,"0")
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
        }
        cb_saturday.setOnClickListener {
            val week = (activity as PillFrequency?)?.getDiesSetmana()
            if(week!![5]=="0"){
                week.set(5,"1")
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
            else{
                week.set(5,"0")
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
        }
        cb_sunday.setOnClickListener {
            val week = (activity as PillFrequency?)?.getDiesSetmana()
            if(week!![6]=="0"){
                week.set(6,"1")
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
            else{
                week.set(6,"0")
                (activity as PillFrequency?)?.setDiesSetmana(week)
            }
        }

    }

}
