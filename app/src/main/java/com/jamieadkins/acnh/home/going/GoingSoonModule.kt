package com.jamieadkins.acnh.home.going

import dagger.Binds
import dagger.Module

@Module
abstract class GoingSoonModule {

    @Binds
    abstract fun presenter(presenter: GoingSoonPresenter): GoingSoonContract.Presenter

}
