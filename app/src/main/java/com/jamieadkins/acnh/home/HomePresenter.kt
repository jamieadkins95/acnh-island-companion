package com.jamieadkins.acnh.home

import com.jamieadkins.acnh.domain.GetCrittersAvailableNowUseCase
import com.jamieadkins.acnh.domain.GetCrittersComingSoonUseCase
import com.jamieadkins.acnh.domain.GetCrittersGoingSoonUseCase
import com.jamieadkins.acnh.domain.GetNewCrittersUseCase
import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.extensions.addToComposite
import com.jamieadkins.acnh.fish.FishCaughtContract
import com.jamieadkins.acnh.fish.FishCaughtPresenter
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val getCrittersAvailableNowUseCase: GetCrittersAvailableNowUseCase,
    private val getCrittersGoingSoonUseCase: GetCrittersGoingSoonUseCase,
    private val getCrittersComingSoonUseCase: GetCrittersComingSoonUseCase,
    private val getNewCrittersUseCase: GetNewCrittersUseCase,
    private val fishCaughtPresenter: FishCaughtPresenter
) : HomeContract.Presenter, FishCaughtContract by fishCaughtPresenter {

    private var view: HomeContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(newView: HomeContract.View) {
        view = newView

        getCrittersAvailableNowUseCase.getCrittersAvailableNow()
            .doOnSubscribe { view?.showLoadingIndicator() }
            .doOnNext { view?.hideLoadingIndicator() }
            .subscribe {
                view?.showCrittersAvailableNow(it, findRarestCritter(it.fish, it.bugs))
            }
            .addToComposite(compositeDisposable)

        getCrittersGoingSoonUseCase.getCrittersGoingSoon()
            .subscribe { view?.showCrittersGoingSoon(it, findRarestCritter(it.fish, it.bugs)) }
            .addToComposite(compositeDisposable)

        getCrittersComingSoonUseCase.getCrittersComingSoon()
            .subscribe { view?.showCrittersComingSoon(it, findRarestCritter(it.fish, it.bugs)) }
            .addToComposite(compositeDisposable)

        getNewCrittersUseCase.getCrittersNewThisMonth()
            .subscribe { view?.showCrittersNewThisMonth(it, findRarestCritter(it.fish, it.bugs)) }
            .addToComposite(compositeDisposable)
    }

    override fun onDetach() {
        compositeDisposable.clear()
        view = null
    }

    private fun findRarestCritter(fish: List<FishEntity>, bugs: List<BugEntity>): Any? {
        val rarestFish = fish.filter { it.caught.not() }.maxBy { it.priceToInt() }
        val rarestBug = bugs.filter { it.caught.not() }.maxBy { it.priceToInt() }
        return rarestFish?.takeIf { (rarestFish.priceToInt()) >= (rarestBug?.priceToInt() ?: 0) } ?: rarestBug
    }

    private fun FishEntity.priceToInt(): Int {
        return this.price.toIntOrNull() ?: 0
    }

    private fun BugEntity.priceToInt(): Int {
        return this.price.toIntOrNull() ?: 0
    }
}