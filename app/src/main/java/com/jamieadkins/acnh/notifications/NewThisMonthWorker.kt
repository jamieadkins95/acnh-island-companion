package com.jamieadkins.acnh.notifications

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
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
        val notification = NotificationCompat.Builder(appContext, "new-this-month")
            .setSmallIcon(R.drawable.ic_palm_tree)
            .setContentTitle(appContext.getString(R.string.new_this_month))
            .setContentText(appContext.getString(R.string.new_this_month_notification, newThisMonth.fish.size + newThisMonth.bugs.size))
            .setColor(ContextCompat.getColor(appContext, R.color.primaryColor))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(getAppIntent(appContext))
            .setAutoCancel(true)

        NotificationManagerCompat.from(appContext).notify(46351, notification.build())
        return Result.success()
    }
}