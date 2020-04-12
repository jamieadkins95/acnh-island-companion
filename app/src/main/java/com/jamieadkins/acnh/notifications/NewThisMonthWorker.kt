package com.jamieadkins.acnh.notifications

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.WorkerParameters
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.domain.GetNewCrittersUseCase
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class NewThisMonthWorker(private val appContext: Context, params: WorkerParameters) : NotificationWorker(appContext, params) {

    @Inject lateinit var getNewCrittersUseCase: GetNewCrittersUseCase

    override fun doWork(): Result {
        getComponent(appContext).inject(this)

        if (ZonedDateTime.now().dayOfMonth != 1) return Result.success()

        val newThisMonth = getNewCrittersUseCase.getCrittersNewThisMonth().blockingFirst()
        createNotificationChannel(appContext, "new-this-month", R.string.new_this_month, R.string.new_this_month_description)
        val pendingIntent = NavDeepLinkBuilder(appContext)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.new_this_month)
            .createPendingIntent()
        val totalCreatures = newThisMonth.fish.size + newThisMonth.bugs.size
        val totalUncaughtCreatures = newThisMonth.uncaughtFish.size + newThisMonth.uncaughtBugs.size
        val text = appContext.resources.getQuantityString(R.plurals.new_this_month_notification, totalUncaughtCreatures, totalCreatures, totalUncaughtCreatures)
        val notification = NotificationCompat.Builder(appContext, "new-this-month")
            .setSmallIcon(R.drawable.ic_palm_tree)
            .setContentTitle(appContext.getString(R.string.new_this_month))
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setContentText(text)
            .setColor(ContextCompat.getColor(appContext, R.color.primaryColor))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        NotificationManagerCompat.from(appContext).notify(46351, notification.build())
        return Result.success()
    }
}