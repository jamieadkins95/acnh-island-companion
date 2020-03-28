package com.jamieadkins.acnh.data.fish

import com.jamieadkins.acnh.domain.FishRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module(includes = [DataModule::class])
abstract class FishDataModule {

    @Binds
    @Reusable
    abstract fun repository(repository: FishRepositoryImpl): FishRepository

}