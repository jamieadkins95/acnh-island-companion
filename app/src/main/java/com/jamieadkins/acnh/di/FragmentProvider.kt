package com.jamieadkins.acnh.di

import com.jamieadkins.acnh.bugs.BugsFragment
import com.jamieadkins.acnh.bugs.BugsModule
import com.jamieadkins.acnh.data.bugs.BugsDataModule
import com.jamieadkins.acnh.data.fish.FishDataModule
import com.jamieadkins.acnh.fish.FishFragment
import com.jamieadkins.acnh.fish.FishModule
import com.jamieadkins.acnh.home.HomeFragment
import com.jamieadkins.acnh.home.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProvider {

    @ContributesAndroidInjector(modules = [FishModule::class, FishDataModule::class])
    abstract fun fish(): FishFragment

    @ContributesAndroidInjector(modules = [BugsModule::class, BugsDataModule::class])
    abstract fun bugs(): BugsFragment

    @ContributesAndroidInjector(modules = [HomeModule::class, FishDataModule::class, BugsDataModule::class])
    abstract fun home(): HomeFragment

}
