package com.jamieadkins.acnh.domain.fish

import com.jamieadkins.acnh.domain.fish.FishEntity
import io.reactivex.Observable

interface FishRepository {

    fun getFish(): Observable<List<FishEntity>>
}