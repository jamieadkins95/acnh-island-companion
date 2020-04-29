package com.jamieadkins.acnh.data.bugs

import com.jamieadkins.acnh.data.DataModule
import com.jamieadkins.acnh.data.HemisphereDataModule
import com.jamieadkins.acnh.domain.bugs.BugRepository
import com.jamieadkins.acnh.domain.fish.FishRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module(includes = [DataModule::class, HemisphereDataModule::class])
abstract class BugsDataModule {

    @Binds
    @Reusable
    abstract fun repository(repository: BugRepositoryImpl): BugRepository

}