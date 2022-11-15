package com.bakanlik112.mobil.datatransfer.base

import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bakanlik112.mobil.datatransfer.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.bakanlik112.mobil.datatransfer.events.UserInfoEvent

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: UserInfoEvent?) {

    }
}