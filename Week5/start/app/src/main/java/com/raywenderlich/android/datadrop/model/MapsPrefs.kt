package com.raywenderlich.android.datadrop.model

import android.preference.PreferenceManager
import com.raywenderlich.android.datadrop.app.DataDropApplication

object MapsPrefs {

    private const val KEY_MARKER_COLOR ="KEY_MARKER_COLOR"
    private const val MAP_TYPE="MAP_TYPE"

    private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(DataDropApplication.getAppContext())

fun saveMarkerColor(markerColor:String){
    val editor = sharedPrefs().edit()
    editor.putString(KEY_MARKER_COLOR,markerColor).apply()
}

    fun getMarkerColor():String = sharedPrefs().getString(KEY_MARKER_COLOR, "Red")

    fun saveMapType(mapType: String){
        val editor = sharedPrefs().edit()
        editor.putString(MAP_TYPE, mapType)
    }

    fun getMapType():String = sharedPrefs().getString(MAP_TYPE, "Normal")
}

