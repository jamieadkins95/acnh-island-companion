package com.jamieadkins.acnh

import android.content.Context
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jamieadkins.acnh.PredictorApplication.Companion.coreComponent
import com.jamieadkins.acnh.databinding.ActivityMainBinding
import com.jamieadkins.acnh.di.DaggerAndroidActivity
import com.jamieadkins.acnh.di.DaggerAppComponent
import com.jamieadkins.acnh.notifications.GoingAwaySoonWorker
import com.jamieadkins.acnh.notifications.NewThisMonthWorker
import java.util.concurrent.TimeUnit

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

        val prefs = getSharedPreferences("acnh-prefs", Context.MODE_PRIVATE)
        val notificationKey = "notifications-setup"
        val setup = prefs.getBoolean(notificationKey, false)
        if (!setup) {
            val newThisMonthWork = PeriodicWorkRequestBuilder<NewThisMonthWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(1, TimeUnit.DAYS).build()
            val goingAwaySoonWork = PeriodicWorkRequestBuilder<GoingAwaySoonWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(1, TimeUnit.DAYS).build()
            val workManager = WorkManager.getInstance(this)
            workManager.enqueueUniquePeriodicWork("new-this-month", ExistingPeriodicWorkPolicy.REPLACE, newThisMonthWork)
            workManager.enqueueUniquePeriodicWork("going-soon", ExistingPeriodicWorkPolicy.REPLACE, goingAwaySoonWork)

            prefs.edit().putBoolean(notificationKey, true).apply()
        }
    }
}
