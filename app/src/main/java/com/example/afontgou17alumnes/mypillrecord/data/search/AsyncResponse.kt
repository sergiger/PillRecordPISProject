package com.example.afontgou17alumnes.mypillrecord.data.search

import com.example.afontgou17alumnes.mypillrecord.data.pills.MyData

interface AsyncResponse {
    fun getResults(result: ArrayList<MyData>)
}