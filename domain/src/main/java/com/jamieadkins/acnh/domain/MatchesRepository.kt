package com.jamieadkins.acnh.domain

import io.reactivex.Observable

interface MatchesRepository {

    fun getMatches(season: Int): Observable<List<GameWeekEntity>>
}