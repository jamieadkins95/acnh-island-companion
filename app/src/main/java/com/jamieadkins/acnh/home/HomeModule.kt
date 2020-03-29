package com.jamieadkins.acnh.home

import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {

    @Binds
    abstract fun presenter(presenter: HomePresenter): HomeContract.Presenter

}
