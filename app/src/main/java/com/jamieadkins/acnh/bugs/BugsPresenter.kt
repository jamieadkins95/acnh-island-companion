package com.jamieadkins.acnh.bugs

import com.jamieadkins.acnh.domain.bugs.GetBugsUseCase
import com.jamieadkins.acnh.domain.fish.GetFishUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class BugsPresenter @Inject constructor(
    private val getBugsUseCase: GetBugsUseCase
) : BugContract.Presenter {

    var view: BugContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(newView: BugContract.View) {
        view = newView

        getBugsUseCase.getBugs()
            .doOnSubscribe { view?.showLoadingIndicator() }
            .doOnNext { view?.hideLoadingIndicator() }
            .subscribe { view?.showBugs(it) }
            .addToComposite(compositeDisposable)
    }

    override fun onDetach() {
        compositeDisposable.clear()
        view = null
    }
}