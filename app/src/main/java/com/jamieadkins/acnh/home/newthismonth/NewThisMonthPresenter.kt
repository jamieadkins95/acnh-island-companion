package com.jamieadkins.acnh.home.newthismonth

import com.jamieadkins.acnh.domain.GetNewCrittersUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import com.jamieadkins.acnh.fish.FishCaughtContract
import com.jamieadkins.acnh.fish.FishCaughtPresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewThisMonthPresenter @Inject constructor(
    private val getNewCrittersUseCase: GetNewCrittersUseCase,
    private val fishCaughtPresenter: FishCaughtPresenter
) : NewThisMonthContract.Presenter, FishCaughtContract by fishCaughtPresenter {

    private var view: NewThisMonthContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(newView: NewThisMonthContract.View) {
        view = newView

        getNewCrittersUseCase.getCrittersNewThisMonth()
            .doOnSubscribe { view?.showLoadingIndicator() }
            .doOnNext { view?.hideLoadingIndicator() }
            .subscribe { view?.showCrittersNewThisMonth(it) }
            .addToComposite(compositeDisposable)
    }

    override fun onDetach() {
        compositeDisposable.clear()
        view = null
    }
}