package com.jamieadkins.acnh.home.available

import com.jamieadkins.acnh.domain.GetCrittersAvailableNowUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import com.jamieadkins.acnh.fish.FishCaughtContract
import com.jamieadkins.acnh.fish.FishCaughtPresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AvailableNowPresenter @Inject constructor(
    private val getCrittersAvailableNowUseCase: GetCrittersAvailableNowUseCase,
    private val fishCaughtPresenter: FishCaughtPresenter
) : AvailableNowContract.Presenter, FishCaughtContract by fishCaughtPresenter {

    private var view: AvailableNowContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(newView: AvailableNowContract.View) {
        view = newView

        getCrittersAvailableNowUseCase.getCrittersAvailableNow()
            .doOnSubscribe { view?.showLoadingIndicator() }
            .doOnNext { view?.hideLoadingIndicator() }
            .subscribe { view?.showCrittersAvailableNow(it) }
            .addToComposite(compositeDisposable)
    }

    override fun onDetach() {
        compositeDisposable.clear()
        view = null
    }
}