package com.jamieadkins.acnh

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jamieadkins.acnh.PredictorApplication.Companion.coreComponent
import com.jamieadkins.acnh.databinding.ActivityMainBinding
import com.jamieadkins.acnh.di.DaggerAndroidActivity
import com.jamieadkins.acnh.di.DaggerAppComponent

class MainActivity : DaggerAndroidActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onInject() {
        DaggerAppComponent.builder()
            .core(coreComponent)
            .activity(this)
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navController = findNavController(this, R.id.nav_host_fragment)
        binding.bottomNav.setupWithNavController(navController)
    }
}
