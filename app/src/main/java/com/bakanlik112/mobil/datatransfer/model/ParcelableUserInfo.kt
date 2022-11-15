package com.bakanlik112.mobil.datatransfer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableUserInfo (

    var isim : String,
    var soyisim : String,
    var yas : Int,
    var boy : Float

) : Parcelable {

}