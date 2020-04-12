package com.jamieadkins.acnh.domain.fish

import com.jamieadkins.acnh.domain.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class ToggleFishCaughtUseCase @Inject constructor(
    private val fishRepository: FishRepository,
    private val schedulerProvider: SchedulerProvider
) {

    fun toggle(fish: FishEntity) = fishRepository.toggleFishCaught(fish.id)
}