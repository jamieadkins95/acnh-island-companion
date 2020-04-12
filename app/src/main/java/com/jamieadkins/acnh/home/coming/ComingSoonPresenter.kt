package com.jamieadkins.acnh.home.coming

import com.jamieadkins.acnh.domain.GetCrittersComingSoonUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import com.jamieadkins.acnh.fish.FishCaughtContract
import com.jamieadkins.acnh.fish.FishCaughtPresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ComingSoonPresenter @Inject constructor(
    private val getCrittersComingSoonUseCase: GetCrittersComingSoonUseCase,
    private val fishCaughtPresenter: FishCaughtPresenter
) : ComingSoonContract.Presenter, FishCaughtContract by fishCaughtPresenter {

    private var view: ComingSoonContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(newView: ComingSoonContract.View) {
        view = newView

        getCrittersComingSoonUseCase.getCrittersComingSoon()
            .doOnSubscribe { view?.showLoadingIndicator() }
            .doOnNext { view?.hideLoadingIndicator() }
            .subscribe { view?.showCrittersComingSoon(it) }
            .addToComposite(compositeDisposable)
    }

    override fun onDetach() {
        compositeDisposable.clear()
        view = null
    }
}