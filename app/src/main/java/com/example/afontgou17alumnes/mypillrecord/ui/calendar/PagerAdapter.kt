package com.example.afontgou17alumnes.mypillrecord.ui.calendar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0-> { return FragmentOne() }
            1-> { return FragmentTwo() }
            else-> { return FragmentThree() }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0-> { "Week"}
            1-> { "Month"}
            else -> return "List"
        }
    }

}
