package com.jamieadkins.acnh.home.newthismonth

import dagger.Binds
import dagger.Module

@Module
abstract class NewThisMonthModule {

    @Binds
    abstract fun presenter(presenter: NewThisMonthPresenter): NewThisMonthContract.Presenter

}
