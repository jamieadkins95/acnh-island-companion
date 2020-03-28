package com.jamieadkins.acnh.di

import android.app.Activity
import com.jamieadkins.acnh.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        PredictionsFragmentProvider::class
    ],
    dependencies = [CoreComponent::class]
)
@PerActivity
interface AppComponent {

    fun inject(target: MainActivity)

    @Component.Builder
    interface Builder {

        fun core(coreComponent: CoreComponent): Builder

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): AppComponent
    }
}