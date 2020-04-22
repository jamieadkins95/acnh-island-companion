package com.jamieadkins.acnh.home.newthismonth

import com.jamieadkins.acnh.bugs.BugCaughtPresenter
import com.jamieadkins.acnh.bugs.BugsCaughtContract
import com.jamieadkins.acnh.domain.GetNewCrittersUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import com.jamieadkins.acnh.fish.FishCaughtContract
import com.jamieadkins.acnh.fish.FishCaughtPresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewThisMonthPresenter @Inject constructor(
    private val getNewCrittersUseCase: GetNewCrittersUseCase,
    private val fishCaughtPresenter: FishCaughtPresenter,
    private val bugCaughtPresenter: BugCaughtPresenter
) : NewThisMonthContract.Presenter, FishCaughtContract by fishCaughtPresenter, BugsCaughtContract by bugCaughtPresenter {

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