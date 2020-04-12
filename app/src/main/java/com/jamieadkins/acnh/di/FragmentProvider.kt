package com.jamieadkins.acnh.di

import com.jamieadkins.acnh.bugs.BugsFragment
import com.jamieadkins.acnh.bugs.BugsModule
import com.jamieadkins.acnh.bugs.profile.BugProfileFragment
import com.jamieadkins.acnh.bugs.profile.BugProfileModule
import com.jamieadkins.acnh.data.bugs.BugsDataModule
import com.jamieadkins.acnh.data.fish.FishDataModule
import com.jamieadkins.acnh.fish.FishFragment
import com.jamieadkins.acnh.fish.FishModule
import com.jamieadkins.acnh.home.HomeFragment
import com.jamieadkins.acnh.home.HomeModule
import com.jamieadkins.acnh.home.coming.ComingSoonFragment
import com.jamieadkins.acnh.home.coming.ComingSoonModule
import com.jamieadkins.acnh.home.going.GoingSoonFragment
import com.jamieadkins.acnh.home.going.GoingSoonModule
import com.jamieadkins.acnh.home.newthismonth.NewThisMonthFragment
import com.jamieadkins.acnh.home.newthismonth.NewThisMonthModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProvider {

    @ContributesAndroidInjector(modules = [FishModule::class, FishDataModule::class])
    abstract fun fish(): FishFragment

    @ContributesAndroidInjector(modules = [BugsModule::class, BugsDataModule::class])
    abstract fun bugs(): BugsFragment

    @ContributesAndroidInjector(modules = [BugProfileModule::class, BugsDataModule::class])
    abstract fun bugProfile(): BugProfileFragment

    @ContributesAndroidInjector(modules = [HomeModule::class, FishDataModule::class, BugsDataModule::class])
    abstract fun home(): HomeFragment

    @ContributesAndroidInjector(modules = [GoingSoonModule::class, FishDataModule::class, BugsDataModule::class])
    abstract fun goingSoon(): GoingSoonFragment

    @ContributesAndroidInjector(modules = [ComingSoonModule::class, FishDataModule::class, BugsDataModule::class])
    abstract fun comingSoon(): ComingSoonFragment

    @ContributesAndroidInjector(modules = [NewThisMonthModule::class, FishDataModule::class, BugsDataModule::class])
    abstract fun newThisMonth(): NewThisMonthFragment

}
