package com.example.afontgou17alumnes.mypillrecord.ui.calendar

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.afontgou17alumnes.mypillrecord.R


class Calendar_fragment : Fragment() {

    companion object {
        fun newInstance() = Calendar_fragment()
    }

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    private lateinit var viewModel: CalendarFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.calendar_fragment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tabLayout = view!!.findViewById<TabLayout>(R.id.tabLayout) //
        viewPager = view!!.findViewById<ViewPager>(R.id.viewPager) //

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Week"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Month"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("List"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = PagerAdapter(fragmentManager)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(p0: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
        })
        viewModel = ViewModelProviders.of(this).get(CalendarFragmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
