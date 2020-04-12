package com.example.afontgou17alumnes.mypillrecord.data

import android.content.Context
import android.content.SharedPreferences
//import com.google.gson.reflect.TypeToken

class Prefs (context: Context) {
    val PREFS_NAME = "Pasword"
    val SHARED_NAME = "Shared name"
    val SHARED_GENDER="Masculin"
    val SHARED_YEAR_BIRTH:Int=1999
    val SHARED_HEIGHT:Float=180F
    val SHARED_WEIGHT:Float=67F
    val SHARED_PASWORD:String="1234567"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var username: String
        get() = prefs.getString(SHARED_NAME, "")
        set(value) = prefs.edit().putString(SHARED_NAME, value).apply()
    var yearBirth: Int
        get()=prefs.getInt(SHARED_YEAR_BIRTH.toString(),-1)
        set(value) = prefs.edit().putInt(SHARED_YEAR_BIRTH.toString(), value).apply()
    var gender: String
        get()=prefs.getString(SHARED_GENDER,"")
        set(value) = prefs.edit().putString(SHARED_GENDER, value).apply()
    var weight: Float
        get()=prefs.getFloat(SHARED_WEIGHT.toString(),0F)
        set(value) = prefs.edit().putFloat(SHARED_WEIGHT.toString(), value).apply()
    var height: Float
        get()=prefs.getFloat(SHARED_HEIGHT.toString(),0F)
        set(value) = prefs.edit().putFloat(SHARED_HEIGHT.toString(), value).apply()
    var pasword: String
        get()=prefs.getString(SHARED_PASWORD,"")
        set(value) = prefs.edit().putString(SHARED_PASWORD, value).apply()

    
}


//data class MyObject(val name: String)