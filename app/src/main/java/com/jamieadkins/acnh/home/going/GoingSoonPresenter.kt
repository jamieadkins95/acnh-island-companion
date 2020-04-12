package com.jamieadkins.acnh.home.going

import com.jamieadkins.acnh.domain.GetCrittersGoingSoonUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GoingSoonPresenter @Inject constructor(
    private val getCrittersGoingSoonUseCase: GetCrittersGoingSoonUseCase
) : GoingSoonContract.Presenter {

    private var view: GoingSoonContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(newView: GoingSoonContract.View) {
        view = newView

        getCrittersGoingSoonUseCase.getCrittersGoingSoon()
            .doOnSubscribe { view?.showLoadingIndicator() }
            .doOnNext { view?.hideLoadingIndicator() }
            .subscribe { view?.showCrittersGoingSoon(it) }
            .addToComposite(compositeDisposable)
    }

    override fun onDetach() {
        compositeDisposable.clear()
        view = null
    }
}