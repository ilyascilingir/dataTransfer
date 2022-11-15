package com.bakanlik112.mobil.datatransfer.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bakanlik112.mobil.datatransfer.base.BaseActivity
import com.bakanlik112.mobil.datatransfer.databinding.ActivityOtherBinding
import com.bakanlik112.mobil.datatransfer.extentions.getParcelableCompat
import com.bakanlik112.mobil.datatransfer.extentions.getSerializableCompat
import com.bakanlik112.mobil.datatransfer.events.UserInfoEvent
import com.bakanlik112.mobil.datatransfer.model.MySingleton
import com.bakanlik112.mobil.datatransfer.model.ParcelableUserInfo
import com.bakanlik112.mobil.datatransfer.model.SerializableUserInfo
import com.bakanlik112.mobil.datatransfer.ui.viewmodels.GetInfoStatus
import com.bakanlik112.mobil.datatransfer.ui.viewmodels.OtherViewModel
import com.bakanlik112.mobil.datatransfer.utils.VeriTransferTypes
import com.bakanlik112.mobil.datatransfer.utils.VeriTransferTypes.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class OtherActivity : BaseActivity() {

    val viewModel by viewModels<OtherViewModel>()
    private lateinit var binding : ActivityOtherBinding

    var isim : String? = null
    var soyisim : String? = null
    var yas : Int? = null
    var boy : Float? = null
    lateinit var veriTransferType : VeriTransferTypes
    lateinit var serializableUserInfo: SerializableUserInfo
    lateinit var parcelableUserInfo: ParcelableUserInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        lifecycleScope.launch {
            getInfoCallBack()
        }


        intent.extras?.getSerializableCompat("veriTransferType",VeriTransferTypes::class.java).let {
            veriTransferType =  it!!
        }


        when(veriTransferType) {

            INTENT -> {

                isim = intent.getStringExtra("isim")
                soyisim = intent.getStringExtra("soyisim")
                yas = intent.getIntExtra("yas",0)
                boy = intent.getFloatExtra("boy",0.0F)

            }

            BUNDLE -> {
                isim = intent.extras?.getString("isim")
                soyisim = intent.extras?.getString("soyisim")
                yas = intent.extras?.getInt("yas")
                boy = intent.extras?.getFloat("boy")

            }

            SERIALIZABLE -> {
                intent.extras?.getSerializableCompat("serializableUserInfo",SerializableUserInfo::class.java).let {
                    serializableUserInfo = it!!
                    isim = it.isim
                    soyisim = it.soyisim
                    yas = it.yas
                    boy = it.boy

                }
            }

            PARCELABLE -> {
                intent.extras?.getParcelableCompat("parcelableUserInfo",ParcelableUserInfo::class.java).let {
                    parcelableUserInfo = it!!
                    isim = it.isim
                    soyisim = it.soyisim
                    yas = it.yas
                    boy = it.boy
                }
            }

            SINGLETON -> {
                isim = MySingleton.singletonUserInfo?.isim
                soyisim = MySingleton.singletonUserInfo?.soyisim
                yas = MySingleton.singletonUserInfo?.yas
                boy = MySingleton.singletonUserInfo?.boy
            }

            SHAREDPREFERENCES -> {
                viewModel.getInfo()
            }

            EVENTBUS -> {
                Toast.makeText(this, "Hoooooooooooooppaaa :D", Toast.LENGTH_SHORT).show()
            }
        }

        writeInfos()

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserInfoEvent(event: UserInfoEvent){
        isim = event.userInfo?.isim.toString()
        soyisim = event.userInfo?.soyisim.toString()
        yas = event.userInfo?.yas
        boy = event.userInfo?.boy
        writeInfos()
    }




    @SuppressLint("SetTextI18n")
    fun writeInfos () {
        binding.sayinText.text = "Sayın $isim  $soyisim ,"
        binding.acKlamaText.text = "$boy boyunuz ve $yas olan yaşınız ile süpersiniz ...  "
        binding.typeAcKlamatext.text = "${veriTransferType.value} ile geldim :)"
    }

    private suspend fun getInfoCallBack(){
        viewModel.viewState4GetInfoInfo.collect{
            when(it){

                GetInfoStatus.ERROR -> {}

                GetInfoStatus.IDLE -> {}

                is GetInfoStatus.SUCCESS -> {

                    // Normal değişken ile alındığında
                   /* isim = it.isim
                    soyisim = it.soyisim
                    yas = it.yas
                    boy = it.boy

                    */

                    // Nesne(model) oluşturup  alındığında
                    isim = it.userInfo.isim
                    soyisim = it.userInfo.soyisim
                    yas = it.userInfo.yas
                    boy = it.userInfo.boy
                }
            }
        }
    }


}