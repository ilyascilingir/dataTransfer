package com.bakanlik112.mobil.datatransfer.ui.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.bakanlik112.mobil.datatransfer.base.BaseViewModel
import com.bakanlik112.mobil.datatransfer.extentions.saveData
import com.bakanlik112.mobil.datatransfer.events.UserInfoEvent
import com.bakanlik112.mobil.datatransfer.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    private val mutableViewState4SaveInfo = MutableStateFlow<SaveInfoStatus>(SaveInfoStatus.IDLE)
    val viewState4SaveInfo: StateFlow<SaveInfoStatus> = mutableViewState4SaveInfo


    fun saveInfo(isim: String, soyisim: String, yas: Int, boy: Float) {

        sharedPreferences.saveData("isim", isim)
        sharedPreferences.saveData("soyisim", soyisim)
        sharedPreferences.saveData("yas", yas)
        sharedPreferences.saveData("boy", boy)

        mutableViewState4SaveInfo.value = SaveInfoStatus.SUCCESS
    }

    fun sendEventBusWithDelay (isim: String, soyisim: String, yas: Int, boy: Float) {

        viewModelScope.launch {
            delay(2000)
            val userInfo = UserInfo(isim,soyisim,yas,boy)
            val userInfoEvent = UserInfoEvent(userInfo)
            EventBus.getDefault().post(userInfoEvent)
        }
    }


    sealed class SaveInfoStatus {
        object IDLE : SaveInfoStatus()
        object SUCCESS : SaveInfoStatus()
        object ERROR : SaveInfoStatus()
    }
}