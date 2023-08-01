package com.example.batterymanager.helper

import android.content.Context
import android.content.SharedPreferences

class SpManager {

    companion object {

        private var sharedPrefrence: SharedPreferences? = null

        private var editor: SharedPreferences.Editor? = null

        private val spb = "SHARED_PREFERENCE_BOOLEAN"

        private val isServiceOn = "is ServiceOn"

        fun isServiceOn(context: Context): Boolean? {
            sharedPrefrence = context.getSharedPreferences(spb, Context.MODE_PRIVATE)
            return sharedPrefrence?.getBoolean(isServiceOn, false)
        }

        fun setServiceState(context: Context, isOn: Boolean?) {
            sharedPrefrence = context.getSharedPreferences(spb, Context.MODE_PRIVATE)
            editor = sharedPrefrence?.edit()
            editor?.putBoolean(
                isServiceOn,
                isOn!!
            )    //-----> two !! in the java meaning : variable if not null ---- > do this ...
            editor?.apply()
        }
    }
}