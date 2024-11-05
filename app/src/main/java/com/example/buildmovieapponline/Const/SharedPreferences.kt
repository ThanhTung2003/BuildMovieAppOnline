package com.example.buildmovieapponline.Const

import android.content.Context
import android.content.SharedPreferences
import com.example.buildmovieapponline.Const.CompanionObject.Companion.ISLOGGEDIN
import com.example.buildmovieapponline.Const.CompanionObject.Companion.MYAPPPREFS

class SharedPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(MYAPPPREFS, Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean){
        sharedPreferences.edit().putBoolean(ISLOGGEDIN, isLoggedIn).apply()
    }

    fun isLoggedIn():Boolean{
        return  sharedPreferences.getBoolean(ISLOGGEDIN, false)
    }

    fun clearSharePreferences(){
        sharedPreferences.edit().clear().apply()
    }
}