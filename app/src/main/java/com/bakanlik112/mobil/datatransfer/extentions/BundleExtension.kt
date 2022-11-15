package com.bakanlik112.mobil.datatransfer.extentions

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by MGunes on 19.10.2022.
 */
@Suppress("UNCHECKED_CAST")
fun <T : Serializable?> Bundle.getSerializableCompat(key: String, clazz: Class<T>): T? {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(key, clazz)!!
    }
    else {
        (getSerializable(key) as T)
    }

}


@Suppress("UNCHECKED_CAST")
fun <T : Parcelable?> Bundle.getParcelableArrayCompat(key: String, clazz: Class<T>): ArrayList<T>? {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArrayList(key, clazz)!!
    } else {
        getParcelableArrayList<T>(key)
    }

}


@Suppress("UNCHECKED_CAST")
fun <T : Parcelable?> Bundle.getParcelableCompat(key: String, clazz: Class<T>): T? {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, clazz)!!
    } else {
        getParcelable<T>(key)
    }

}