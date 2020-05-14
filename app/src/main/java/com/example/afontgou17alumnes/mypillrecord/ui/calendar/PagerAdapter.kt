package com.example.afontgou17alumnes.mypillrecord.ui.calendar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.List.FragmentThree
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.Week.FragmentOne

class PagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0-> { return FragmentOne()
            }
            1-> { return FragmentTwo() }
            else-> { return FragmentThree()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0-> { "Planned"}
            1-> { "Month"}
            else -> return "Historic"
        }
    }

}
