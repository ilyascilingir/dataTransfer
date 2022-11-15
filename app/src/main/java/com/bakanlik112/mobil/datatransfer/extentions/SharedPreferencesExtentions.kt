package com.bakanlik112.mobil.datatransfer.extentions

import android.content.SharedPreferences

fun SharedPreferences.saveData(key: String, value: String) {
    val editor = edit()
    editor.putString(key, value)
    editor.apply()
}

fun SharedPreferences.saveData(key: String, value: Boolean) {
    val editor = edit()
    editor.putBoolean(key, value)
    editor.apply()
}

fun SharedPreferences.saveData(key: String, value: Int) {
    val editor = edit()
    editor.putInt(key, value)
    editor.apply()
}
fun SharedPreferences.saveData(key: String, value: Float) {
    val editor = edit()
    editor.putFloat(key, value)
    editor.apply()
}

fun SharedPreferences.getStringData(key: String): String {
    return getString(key, "")!!
}

fun SharedPreferences.getIntData(key: String): Int {
    return getInt(key, 0)
}
fun SharedPreferences.getFloatData(key: String): Float {
    return getFloat(key, 0F)
}

fun SharedPreferences.removeData(key: String) {
    edit().remove(key).apply()
}