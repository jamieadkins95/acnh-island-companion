package com.jamieadkins.acnh.domain.fish

import io.reactivex.Observable

interface FishRepository {

    fun getFish(): Observable<List<FishEntity>>

    fun toggleFishCaught(fishId: String)
}