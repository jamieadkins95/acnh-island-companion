package com.jamieadkins.acnh.domain

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Test

class GetPredictionsForSeasonUseCaseTest {

    private val matchesRepository: MatchesRepository = mock()
    private val predictionsRepository: PredictionsRepository = mock()
    private val getPredictionsForSeasonUseCase = GetPredictionsForSeasonUseCase(matchesRepository, predictionsRepository)


    @Test fun `User Prediction Overrides Real Outcome`() {
        val realMatch = MatchEntity(100, newTeam(1), newTeam(2), MatchOutcomeEntity.HomeTeamWin)
        val gameWeek = GameWeekEntity(listOf(realMatch))
        val predictions = mapOf(100L to MatchOutcomeEntity.AwayTeamWin)
        whenever(matchesRepository.getMatches(any())).thenReturn(Observable.just(listOf(gameWeek)))
        whenever(predictionsRepository.getPredictionsForSeason(any())).thenReturn(Observable.just(predictions))

        val overwrittenMatch = realMatch.copy(outcome = MatchOutcomeEntity.AwayTeamWin)

        val testObserver = getPredictionsForSeasonUseCase.getSeason(2019).test()
        testObserver.assertNoErrors()
        testObserver.assertValue(PredictionState(2019, listOf(GameWeekEntity(listOf(overwrittenMatch)))))
    }

    private fun newTeam(id: Long): TeamEntity {
        return TeamEntity(id, "Name", "ABBR", "")
    }

    @Test fun `No predictions, only real outcomes used`() {
        val realMatch = MatchEntity(100, newTeam(1), newTeam(2), MatchOutcomeEntity.HomeTeamWin)
        val gameWeek = GameWeekEntity(listOf(realMatch))
        val predictions = emptyMap<Long, MatchOutcomeEntity>()
        whenever(matchesRepository.getMatches(any())).thenReturn(Observable.just(listOf(gameWeek)))
        whenever(predictionsRepository.getPredictionsForSeason(any())).thenReturn(Observable.just(predictions))

        val testObserver = getPredictionsForSeasonUseCase.getSeason(2019).test()
        testObserver.assertNoErrors()
        testObserver.assertValue(PredictionState(2019, listOf(gameWeek)))
    }
}