package com.jamieadkins.acnh.fish

import com.jamieadkins.acnh.domain.fish.GetFishUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FishPresenter @Inject constructor(
    private val getFishUseCase: GetFishUseCase,
    private val fishCaughtPresenter: FishCaughtPresenter
) : FishContract.Presenter, FishCaughtContract by fishCaughtPresenter {

    private var view: FishContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(newView: FishContract.View) {
        view = newView

        getFishUseCase.getFish()
            .doOnSubscribe { view?.showLoadingIndicator() }
            .doOnNext { view?.hideLoadingIndicator() }
            .subscribe { view?.showFish(it) }
            .addToComposite(compositeDisposable)
    }

    override fun onDetach() {
        compositeDisposable.clear()
        view = null
    }
}