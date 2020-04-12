package com.jamieadkins.acnh.home.newthismonth

import com.jamieadkins.acnh.domain.GetNewCrittersUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewThisMonthPresenter @Inject constructor(
    private val getNewCrittersUseCase: GetNewCrittersUseCase
) : NewThisMonthContract.Presenter {

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