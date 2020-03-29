package com.jamieadkins.acnh.bugs

import dagger.Binds
import dagger.Module

@Module
abstract class BugsModule {

    @Binds
    abstract fun presenter(presenter: BugsPresenter): BugContract.Presenter

}
