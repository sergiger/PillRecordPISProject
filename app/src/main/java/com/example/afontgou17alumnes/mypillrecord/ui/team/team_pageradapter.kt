package com.example.afontgou17alumnes.mypillrecord.ui.team

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.FragmentTwo
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.List.FragmentThree
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.Week.FragmentOne

class team_pageradapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0-> { return team_fragmentone()
            }
            1-> { return team_fragmenttwo() }
            else-> { return team_fragmentthree()
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