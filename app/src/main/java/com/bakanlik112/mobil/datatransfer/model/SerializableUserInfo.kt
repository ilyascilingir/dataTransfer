package com.bakanlik112.mobil.datatransfer.model

import java.io.Serializable

data class SerializableUserInfo (

    var isim : String,
    var soyisim : String,
    var yas : Int,
    var boy : Float
    ) : Serializable {

}