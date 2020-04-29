package com.jamieadkins.acnh.data

import com.jamieadkins.acnh.domain.HemisphereRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
abstract class HemisphereDataModule {

    @Binds
    @Reusable
    abstract fun repository(repository: HemisphereRepositoryImpl): HemisphereRepository

}