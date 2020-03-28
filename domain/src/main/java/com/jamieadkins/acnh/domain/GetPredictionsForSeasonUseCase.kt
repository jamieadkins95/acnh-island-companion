package com.jamieadkins.acnh.domain

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetPredictionsForSeasonUseCase @Inject constructor(
    private val matchesRepository: MatchesRepository,
    private val predictionsRepository: PredictionsRepository
) {

    fun getSeason(season: Int): Observable<PredictionState> {
        return Observable.combineLatest(
            matchesRepository.getMatches(season),
            predictionsRepository.getPredictionsForSeason(season),
            BiFunction { gameWeeks, predictions ->
                val predictedMatches = gameWeeks.map { gameWeek ->
                    val newMatches = gameWeek.matches.map { match ->
                        match.copy(outcome = predictions[match.id] ?: match.outcome)
                    }
                    GameWeekEntity(newMatches)
                }
                PredictionState(season, predictedMatches)
            }
        )
    }
}