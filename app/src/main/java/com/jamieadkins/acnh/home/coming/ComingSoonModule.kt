package com.jamieadkins.acnh.home.coming

import dagger.Binds
import dagger.Module

@Module
abstract class ComingSoonModule {

    @Binds
    abstract fun presenter(presenter: ComingSoonPresenter): ComingSoonContract.Presenter

}
