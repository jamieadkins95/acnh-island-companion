package com.jamieadkins.acnh.notifications

import com.jamieadkins.acnh.data.bugs.BugsDataModule
import com.jamieadkins.acnh.data.fish.FishDataModule
import com.jamieadkins.acnh.di.CoreComponent
import com.jamieadkins.acnh.di.PerActivity
import com.jamieadkins.acnh.di.SchedulerProviderModule
import dagger.Component

@Component(
    modules = [
        FishDataModule::class,
        BugsDataModule::class,
        SchedulerProviderModule::class
    ],
    dependencies = [CoreComponent::class]
)
@PerActivity
interface NotificationWorkerComponent {

    fun inject(target: NewThisMonthWorker)
    fun inject(target: GoingAwaySoonWorker)

    @Component.Builder
    interface Builder {

        fun core(coreComponent: CoreComponent): Builder

        fun build(): NotificationWorkerComponent
    }
}