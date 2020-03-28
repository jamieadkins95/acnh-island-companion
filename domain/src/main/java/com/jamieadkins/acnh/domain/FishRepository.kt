package com.jamieadkins.acnh.domain

import io.reactivex.Observable

interface FishRepository {

    fun getFish(): Observable<List<FishEntity>>
}