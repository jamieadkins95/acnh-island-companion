package com.jamieadkins.acnh.notifications

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.WorkerParameters
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.domain.GetCrittersGoingSoonUseCase
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class GoingAwaySoonWorker(private val appContext: Context, params: WorkerParameters) : NotificationWorker(appContext, params) {

    @Inject lateinit var getCrittersGoingSoonUseCase: GetCrittersGoingSoonUseCase

    override fun doWork(): Result {
        getComponent(appContext).inject(this)

        if (ZonedDateTime.now().dayOfMonth != 21) return Result.success()

        val goingSoon = getCrittersGoingSoonUseCase.getCrittersGoingSoon().blockingFirst()

        createNotificationChannel(appContext, "going-soon", R.string.going_away_soon, R.string.going_away_soon_description)

        val title = appContext.getString(R.string.going_away_soon_notification, goingSoon.fish.size + goingSoon.bugs.size)
        val text = appContext.getString(R.string.going_away_soon_notification_description)
        val notification = NotificationCompat.Builder(appContext, "going-soon")
            .setSmallIcon(R.drawable.ic_palm_tree)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setColor(ContextCompat.getColor(appContext, R.color.primaryColor))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(getAppIntent(appContext))
            .setAutoCancel(true)

        NotificationManagerCompat.from(appContext).notify(476823, notification.build())
        return Result.success()
    }
}