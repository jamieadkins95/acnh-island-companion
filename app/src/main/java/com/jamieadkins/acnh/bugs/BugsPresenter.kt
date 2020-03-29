package com.jamieadkins.acnh.bugs

import com.jamieadkins.acnh.domain.bugs.GetBugsUseCase
import com.jamieadkins.acnh.domain.fish.GetFishUseCase
import javax.inject.Inject

class BugsPresenter @Inject constructor(
    private val getBugsUseCase: GetBugsUseCase
) : BugContract.Presenter {

    var view: BugContract.View? = null

    override fun onAttach(newView: BugContract.View) {
        view = newView

        getBugsUseCase.getBugs()
            .subscribe {
                view?.showBugs(it)
            }
    }

    override fun onDetach() {
        view = null
    }
}