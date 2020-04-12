package com.jamieadkins.acnh.home.available

import dagger.Binds
import dagger.Module

@Module
abstract class AvailableNowModule {

    @Binds
    abstract fun presenter(presenter: AvailableNowPresenter): AvailableNowContract.Presenter

}
