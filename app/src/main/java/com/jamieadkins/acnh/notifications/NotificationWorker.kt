package com.jamieadkins.acnh.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.StringRes
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jamieadkins.acnh.MainActivity
import com.jamieadkins.acnh.PredictorApplication.Companion.coreComponent
import com.jamieadkins.acnh.domain.GetNewCrittersUseCase
import javax.inject.Inject

abstract class NotificationWorker(appContext: Context, params: WorkerParameters) : Worker(appContext, params) {

    fun getComponent(context: Context): NotificationWorkerComponent {
        return DaggerNotificationWorkerComponent.builder()
            .core(context.coreComponent)
            .build()
    }

    fun createNotificationChannel(context: Context, id: String, @StringRes nameRes: Int, @StringRes descriptionRes: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(nameRes)
            val descriptionText = context.getString(descriptionRes)
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(id, name, importance)
            mChannel.description = descriptionText
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun getAppIntent(context: Context, data: Uri? = null): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.data = data
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}