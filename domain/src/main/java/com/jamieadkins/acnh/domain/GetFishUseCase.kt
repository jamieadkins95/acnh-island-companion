package com.jamieadkins.acnh.domain

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetFishUseCase @Inject constructor(
    private val fishRepository: FishRepository
) {

    fun getFish(): Observable<List<FishEntity>> {
        return fishRepository.getFish()
    }
}