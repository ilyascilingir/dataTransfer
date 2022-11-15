package com.bakanlik112.mobil.datatransfer.utils

enum class VeriTransferTypes(val value : String){
    INTENT("Intent"),
    BUNDLE("Bundle"),
    SERIALIZABLE("Serializable"),
    PARCELABLE("Parcelable"),
    SINGLETON("Singleton"),
    SHAREDPREFERENCES("Shared Preferences"),
    EVENTBUS("Eventbus")
}