package com.jamieadkins.acnh.di

import com.jamieadkins.acnh.fish.FishFragment
import com.jamieadkins.acnh.fish.FishModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProvider {

    @ContributesAndroidInjector(modules = [FishModule::class])
    abstract fun fish(): FishFragment

}
