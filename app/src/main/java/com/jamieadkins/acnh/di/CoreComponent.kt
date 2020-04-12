package com.jamieadkins.acnh.di

import android.content.Context
import com.jamieadkins.acnh.PredictorApplication
import com.jamieadkins.acnh.data.ApplicationContext
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [])
@Singleton
interface CoreComponent {

    @ApplicationContext
    fun exposeContext(): Context

    fun inject(target: PredictorApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(@ApplicationContext context: Context): Builder

        fun build(): CoreComponent
    }
}