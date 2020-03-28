package com.jamieadkins.acnh

import android.os.Bundle
import com.jamieadkins.acnh.PredictorApplication.Companion.coreComponent
import com.jamieadkins.acnh.di.DaggerAndroidActivity
import com.jamieadkins.acnh.di.DaggerAppComponent

class MainActivity : DaggerAndroidActivity() {

    override fun onInject() {
        DaggerAppComponent.builder()
            .core(coreComponent)
            .activity(this)
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
