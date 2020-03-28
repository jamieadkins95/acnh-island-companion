package com.jamieadkins.acnh.domain

import io.reactivex.Observable

interface PredictionsRepository {

    fun getPredictionsForSeason(season: Int): Observable<Map<Long, MatchOutcomeEntity>>
}