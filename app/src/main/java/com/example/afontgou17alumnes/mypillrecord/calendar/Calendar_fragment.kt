package com.example.afontgou17alumnes.mypillrecord.calendar

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.afontgou17alumnes.mypillrecord.R
import kotlinx.android.synthetic.main.calendar_fragment_fragment.*


class Calendar_fragment : Fragment() {

    companion object {
        fun newInstance() =
            Calendar_fragment()
    }

    private lateinit var viewModel: CalendarFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.calendar_fragment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Alteracions abaix
        toolBar.setTitle("TabLayout")
        // setSupportActionBar(toolBar)
        //val fragmentAdapter = PagerAdapter(supportFragmentManager)
        //viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
        // Fins aqui
        viewModel = ViewModelProviders.of(this).get(CalendarFragmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
