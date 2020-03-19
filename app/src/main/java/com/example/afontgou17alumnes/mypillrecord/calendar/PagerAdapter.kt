package com.example.afontgou17alumnes.mypillrecord.calendar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0-> { FragmentOne() }
            1-> { FragmentTwo() }
            else-> { return FragmentThree() }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0-> { "One"}
            1-> { "Two"}
            else-> { return "Three"}
        }
    }
}
