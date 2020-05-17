package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.CalendarFragmentViewModel
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.PagerAdapter
import com.google.android.material.tabs.TabLayout

class team_calendar_fragment : Fragment() {

    companion object {
        fun newInstance() = team_calendar_fragment()
    }

    private lateinit var viewModel: CalendarFragmentViewModel
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Controller.main_activity_fragment=1
        return inflater.inflate(R.layout.activity_team_calendar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createTabs()
    }

    fun createTabs() {
        tabLayout = view!!.findViewById<TabLayout>(R.id.tabLayout)
        viewPager = view!!.findViewById<ViewPager>(R.id.viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Planned"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Month"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Historic"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = team_pageradapter(childFragmentManager)
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                //viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
        })
        viewPager!!.currentItem=0
        viewModel = ViewModelProviders.of(this).get(CalendarFragmentViewModel::class.java)
        // TODO: Use the ViewModel
    }
}