package com.jamieadkins.acnh.di

import com.jamieadkins.acnh.predictions.PredictionsFragment
import com.jamieadkins.acnh.predictions.PredictionsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PredictionsFragmentProvider {

    @ContributesAndroidInjector(modules = [PredictionsModule::class])
    abstract fun predictions(): PredictionsFragment

}
