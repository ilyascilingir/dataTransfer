package com.bakanlik112.mobil.datatransfer.ui.viewmodels

import android.content.SharedPreferences
import com.bakanlik112.mobil.datatransfer.base.BaseViewModel
import com.bakanlik112.mobil.datatransfer.extentions.getFloatData
import com.bakanlik112.mobil.datatransfer.extentions.getIntData
import com.bakanlik112.mobil.datatransfer.extentions.getStringData
import com.bakanlik112.mobil.datatransfer.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OtherViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    private val mutableViewState4GetInfo = MutableStateFlow<GetInfoStatus>(GetInfoStatus.IDLE)
    val viewState4GetInfoInfo: StateFlow<GetInfoStatus> = mutableViewState4GetInfo
    private var userInfo : UserInfo? = null

    fun getInfo () {

        val isim = sharedPreferences.getStringData("isim")
        val soyisim = sharedPreferences.getStringData("soyisim")
        val yas = sharedPreferences.getIntData("yas")
        val boy = sharedPreferences.getFloatData("boy")

        userInfo = UserInfo(isim,soyisim,yas, boy)
        mutableViewState4GetInfo.value = GetInfoStatus.SUCCESS(userInfo!!)
    }

}

sealed class GetInfoStatus {
    object IDLE : GetInfoStatus()
    data class SUCCESS(var userInfo: UserInfo) : GetInfoStatus()
    //data class SUCCESS(var isim : String, var soyisim : String, var yas : String, var boy : String) : GetInfoStatus()
    object ERROR : GetInfoStatus()
}
