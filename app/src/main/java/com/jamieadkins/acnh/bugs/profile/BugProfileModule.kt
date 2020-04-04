package com.jamieadkins.acnh.bugs.profile

import dagger.Binds
import dagger.Module

@Module
abstract class BugProfileModule {

    @Binds
    abstract fun presenter(presenter: BugProfilePresenter): BugProfileContract.Presenter

}
