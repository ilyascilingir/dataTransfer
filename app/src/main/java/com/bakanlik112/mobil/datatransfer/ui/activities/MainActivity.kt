package com.bakanlik112.mobil.datatransfer.ui.activities

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bakanlik112.mobil.datatransfer.base.BaseActivity
import com.bakanlik112.mobil.datatransfer.databinding.ActivityMainBinding
import com.bakanlik112.mobil.datatransfer.model.MySingleton
import com.bakanlik112.mobil.datatransfer.model.ParcelableUserInfo
import com.bakanlik112.mobil.datatransfer.model.SerializableUserInfo
import com.bakanlik112.mobil.datatransfer.model.SingletonUserInfo
import com.bakanlik112.mobil.datatransfer.ui.viewmodels.MainViewModel
import com.bakanlik112.mobil.datatransfer.utils.VeriTransferTypes
import com.bakanlik112.mobil.datatransfer.utils.VeriTransferTypes.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    var selectedItem: VeriTransferTypes = INTENT
    var isim : String? = null
    var soyisim : String? = null
    var yas : Int? = null
    var boy : Float? = null
    lateinit var nextPageIntent : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        nextPageIntent = Intent(this,OtherActivity::class.java)

        //-------------------- Spinner -------------------------

        val transferTypeTitle = VeriTransferTypes.values().map { it.value }

        val dataTransferAdapter = ArrayAdapter<Any> (this,R.layout.simple_spinner_dropdown_item,transferTypeTitle)

        binding.spinnerTransferType.adapter = dataTransferAdapter

        binding.spinnerTransferType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                VeriTransferTypes.values().forEach {
                    if (it.value.contains(parent?.getItemAtPosition(position).toString(), true)) {
                        selectedItem = it
                    }
                }
            }
        }

        binding.spinnerTransferType.setSelection(0)

        //-----------------------------------------------------

        ilerleButtonClicked()

        lifecycleScope.launch {
            saveInfoCallBack()
        }
    }


    fun ilerleButtonClicked(){

        binding.aktarButton.setOnClickListener {

            isim = binding.isimText.text.toString()
            soyisim = binding.soyisimText.text.toString()
            yas = binding.yasText.text.toString().toIntOrNull()
            boy = binding.boyText.text.toString().toFloatOrNull()

            nextPageIntent.putExtra("veriTransferType", selectedItem)
            val isAllBlanksFilled : Boolean = !isim.isNullOrEmpty() && !soyisim.isNullOrEmpty() && yas.toString().isNotEmpty() && boy.toString().isNotEmpty()

            when (selectedItem) {

                INTENT -> {
                    if( isAllBlanksFilled){
                        nextPageIntent.putExtra("isim", isim)
                        nextPageIntent.putExtra("soyisim", soyisim)
                        nextPageIntent.putExtra("yas", yas)
                        nextPageIntent.putExtra("boy", boy)
                        startActivity(nextPageIntent)
                    }else{
                        Toast.makeText(this,"Lütfen tüm alanları doldurunuz!",Toast.LENGTH_LONG).show()
                    }
                }

                BUNDLE-> {
                    if (isAllBlanksFilled){
                        val bundle = Bundle()
                        bundle.putString("isim", isim)
                        bundle.putString("soyisim", soyisim)
                        yas?.let{ yas -> bundle.putInt("yas",yas)}
                        boy?.let{ boy -> bundle.putFloat("boy",boy)}
                        nextPageIntent.putExtras(bundle)
                        startActivity(nextPageIntent)
                    }else{
                        Toast.makeText(this,"Lütfen tüm alanları doldurunuz!",Toast.LENGTH_LONG).show()
                    }
                }

                SERIALIZABLE -> {
                    if (isAllBlanksFilled){
                        val serializableUserInfo = SerializableUserInfo(isim!!, soyisim!!, yas!!,boy!!)
                        nextPageIntent.putExtra("serializableUserInfo", serializableUserInfo)
                        startActivity(nextPageIntent)
                    }else{
                        Toast.makeText(this,"Lütfen tüm alanları doldurunuz!",Toast.LENGTH_LONG).show()
                    }
                }

                PARCELABLE -> {
                    if (isAllBlanksFilled){
                        val parcelableUserInfo = ParcelableUserInfo(isim!!, soyisim!!, yas!!,boy!!)
                        nextPageIntent.putExtra("parcelableUserInfo", parcelableUserInfo)
                        startActivity(nextPageIntent)
                    }else{
                        Toast.makeText(this,"Lütfen tüm alanları doldurunuz!",Toast.LENGTH_LONG).show()
                    }
                }

                SINGLETON -> {
                    if (isAllBlanksFilled){

                        MySingleton.singletonUserInfo = SingletonUserInfo(isim, soyisim, yas, boy)
                        startActivity(nextPageIntent)
                    }else{
                        Toast.makeText(this,"Lütfen tüm alanları doldurunuz!",Toast.LENGTH_LONG).show()
                    }

                }

                SHAREDPREFERENCES -> {
                    if( isAllBlanksFilled){
                        viewModel.saveInfo(isim!!,soyisim!!,yas!!,boy!!)
                    }else{
                        Toast.makeText(this,"Lütfen tüm alanları doldurunuz!",Toast.LENGTH_LONG).show()
                    }
                }

                EVENTBUS -> {
                    if (isAllBlanksFilled){
                        viewModel.sendEventBusWithDelay(isim!!,soyisim!!,yas!!,boy!!)
                        startActivity(nextPageIntent)
                    }else{
                        Toast.makeText(this,"Lütfen tüm alanları doldurunuz!",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    @SuppressLint("ShowToast")
    private suspend fun saveInfoCallBack(){
        viewModel.viewState4SaveInfo.collect{
            when(it){
                MainViewModel.SaveInfoStatus.ERROR -> {}
                MainViewModel.SaveInfoStatus.IDLE -> {}
                MainViewModel.SaveInfoStatus.SUCCESS -> {
                    Toast.makeText(this,"Bilgiler başarıyla kaydedildi.",Toast.LENGTH_LONG)
                    startActivity(nextPageIntent)
                }
            }
        }
    }
}

