package com.jamieadkins.acnh.predictions

import dagger.Binds
import dagger.Module

@Module
abstract class PredictionsModule {

    @Binds
    abstract fun presenter(presenter: PredictionsPresenter): PredictionsContract.Presenter

}
