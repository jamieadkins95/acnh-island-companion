package com.jamieadkins.acnh.di

import com.jamieadkins.acnh.SchedulerProviderImpl
import com.jamieadkins.acnh.domain.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class SchedulerProviderModule {

    @Binds
    abstract fun schedulerProvider(schedulerProviderImpl: SchedulerProviderImpl) : SchedulerProvider
}