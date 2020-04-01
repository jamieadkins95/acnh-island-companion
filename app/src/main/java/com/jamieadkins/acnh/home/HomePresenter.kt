package com.jamieadkins.acnh.home

import com.jamieadkins.acnh.domain.GetCrittersAvailableNowUseCase
import com.jamieadkins.acnh.domain.GetCrittersComingSoonUseCase
import com.jamieadkins.acnh.domain.GetCrittersGoingSoonUseCase
import com.jamieadkins.acnh.domain.GetNewCrittersUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val getCrittersAvailableNowUseCase: GetCrittersAvailableNowUseCase,
    private val getCrittersGoingSoonUseCase: GetCrittersGoingSoonUseCase,
    private val getCrittersComingSoonUseCase: GetCrittersComingSoonUseCase,
    private val getNewCrittersUseCase: GetNewCrittersUseCase
) : HomeContract.Presenter {

    private var view: HomeContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(newView: HomeContract.View) {
        view = newView

        getCrittersAvailableNowUseCase.getCrittersAvailableNow()
            .doOnSubscribe { view?.showLoadingIndicator() }
            .doOnNext { view?.hideLoadingIndicator() }
            .subscribe { view?.showCrittersAvailableNow(it) }
            .addToComposite(compositeDisposable)

        getCrittersGoingSoonUseCase.getCrittersGoingSoon()
            .subscribe { view?.showCrittersGoingSoon(it) }
            .addToComposite(compositeDisposable)

        getCrittersComingSoonUseCase.getCrittersComingSoon()
            .subscribe { view?.showCrittersComingSoon(it) }
            .addToComposite(compositeDisposable)

        getNewCrittersUseCase.getCrittersNewThisMonth()
            .subscribe { view?.showCrittersNewThisMonth(it) }
            .addToComposite(compositeDisposable)
    }

    override fun onDetach() {
        compositeDisposable.clear()
        view = null
    }
}