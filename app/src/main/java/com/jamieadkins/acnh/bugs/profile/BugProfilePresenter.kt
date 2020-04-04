package com.jamieadkins.acnh.bugs.profile

import com.jamieadkins.acnh.domain.bugs.GetBugUseCase
import com.jamieadkins.acnh.domain.bugs.GetBugsUseCase
import com.jamieadkins.acnh.domain.fish.GetFishUseCase
import com.jamieadkins.acnh.extensions.addToComposite
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class BugProfilePresenter @Inject constructor(
    private val getBugUseCase: GetBugUseCase
) : BugProfileContract.Presenter {

    var view: BugProfileContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    private val currentBugId = BehaviorSubject.create<String>()

    override fun onAttach(newView: BugProfileContract.View) {
        view = newView
        currentBugId.switchMap(getBugUseCase::getBug)
            .doOnSubscribe { view?.showLoadingIndicator() }
            .doOnNext { view?.hideLoadingIndicator() }
            .subscribe { view?.showBug(it) }
            .addToComposite(compositeDisposable)
    }

    override fun init(bugId: String) = currentBugId.onNext(bugId)

    override fun onDetach() {
        compositeDisposable.clear()
        view = null
    }
}