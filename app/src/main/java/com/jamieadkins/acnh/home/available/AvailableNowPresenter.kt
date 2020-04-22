package com.jamieadkins.acnh.home.available

import com.jamieadkins.acnh.bugs.BugCaughtPresenter
import com.jamieadkins.acnh.bugs.BugsCaughtContract
import com.jamieadkins.acnh.domain.GetCrittersAvailableNowUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import com.jamieadkins.acnh.fish.FishCaughtContract
import com.jamieadkins.acnh.fish.FishCaughtPresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AvailableNowPresenter @Inject constructor(
    private val getCrittersAvailableNowUseCase: GetCrittersAvailableNowUseCase,
    private val fishCaughtPresenter: FishCaughtPresenter,
    private val bugCaughtPresenter: BugCaughtPresenter
) : AvailableNowContract.Presenter, FishCaughtContract by fishCaughtPresenter, BugsCaughtContract by bugCaughtPresenter {

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