package com.jamieadkins.acnh.fish

import dagger.Binds
import dagger.Module

@Module
abstract class FishModule {

    @Binds
    abstract fun presenter(presenter: FishPresenter): FishContract.Presenter

}
