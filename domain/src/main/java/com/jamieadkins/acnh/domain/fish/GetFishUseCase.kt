package com.jamieadkins.acnh.domain.fish

import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.domain.fish.FishRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetFishUseCase @Inject constructor(
    private val fishRepository: FishRepository
) {

    fun getFish(): Observable<List<FishEntity>> {
        return fishRepository.getFish()
    }
}