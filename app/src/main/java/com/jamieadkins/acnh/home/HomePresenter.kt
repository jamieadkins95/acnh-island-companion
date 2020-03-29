package com.jamieadkins.acnh.home

import com.jamieadkins.acnh.domain.GetCrittersAvailableNowUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val getCrittersAvailableNowUseCase: GetCrittersAvailableNowUseCase
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
    }

    override fun onDetach() {
        compositeDisposable.clear()
        view = null
    }
}