package com.jamieadkins.acnh.home.coming

import com.jamieadkins.acnh.bugs.BugCaughtPresenter
import com.jamieadkins.acnh.bugs.BugsCaughtContract
import com.jamieadkins.acnh.domain.GetCrittersComingSoonUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import com.jamieadkins.acnh.fish.FishCaughtContract
import com.jamieadkins.acnh.fish.FishCaughtPresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ComingSoonPresenter @Inject constructor(
    private val getCrittersComingSoonUseCase: GetCrittersComingSoonUseCase,
    private val fishCaughtPresenter: FishCaughtPresenter,
    private val bugCaughtPresenter: BugCaughtPresenter
) : ComingSoonContract.Presenter, FishCaughtContract by fishCaughtPresenter, BugsCaughtContract by bugCaughtPresenter {

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