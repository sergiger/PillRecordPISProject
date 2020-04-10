package com.example.afontgou17alumnes.mypillrecord.data.pills

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class ListAdapter (val context: Context, val list: ArrayList<MyData>): BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

}