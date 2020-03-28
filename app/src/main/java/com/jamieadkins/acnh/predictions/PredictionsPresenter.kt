package com.jamieadkins.acnh.predictions

import com.jamieadkins.acnh.domain.GameWeekEntity
import com.jamieadkins.acnh.domain.MatchEntity
import com.jamieadkins.acnh.domain.MatchOutcomeEntity
import com.jamieadkins.acnh.domain.PredictionState
import com.jamieadkins.acnh.domain.TeamEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

class PredictionsPresenter @Inject constructor() : PredictionsContract.Presenter {

    var view: PredictionsContract.View? = null

    override fun onAttach(newView: PredictionsContract.View) {
        view = newView

        Observable.interval(5, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val saints = TeamEntity(
                    1, "Saints", "NO", "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/kc.png"
                )

                val arizona = TeamEntity(
                    2, "Cardinals", "ARI", "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/kc.png"
                )
                val match = MatchEntity(1, arizona, saints, getRandomOutcome())
                PredictionState(
                    2019,
                    listOf(
                        GameWeekEntity(
                            listOf(match, match, match, match, match, match, match, match, match, match)
                        )
                    )
                )
            }
            .subscribe {
                view?.showPredictions(it)
            }
    }

    override fun onDetach() {
        view = null
    }

    override fun makePrediction(matchId: Long, teamId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getRandomOutcome(): MatchOutcomeEntity {
        return when (Random.nextInt(0, 3)) {
            0 -> MatchOutcomeEntity.HomeTeamWin
            1 -> MatchOutcomeEntity.AwayTeamWin
            2 -> MatchOutcomeEntity.Tie
            else -> MatchOutcomeEntity.HomeTeamWin
        }
    }
}