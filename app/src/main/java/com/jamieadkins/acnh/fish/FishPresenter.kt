package com.jamieadkins.acnh.fish

import com.jamieadkins.acnh.domain.fish.GetFishUseCase
import javax.inject.Inject

class FishPresenter @Inject constructor(
    private val getFishUseCase: GetFishUseCase
) : FishContract.Presenter {

    var view: FishContract.View? = null

    override fun onAttach(newView: FishContract.View) {
        view = newView

        getFishUseCase.getFish()
            .subscribe {
                view?.showFish(it)
            }
    }

    override fun onDetach() {
        view = null
    }
}