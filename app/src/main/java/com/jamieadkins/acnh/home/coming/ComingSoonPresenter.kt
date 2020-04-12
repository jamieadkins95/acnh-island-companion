package com.jamieadkins.acnh.home.coming

import com.jamieadkins.acnh.domain.GetCrittersComingSoonUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ComingSoonPresenter @Inject constructor(
    private val getCrittersComingSoonUseCase: GetCrittersComingSoonUseCase
) : ComingSoonContract.Presenter {

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