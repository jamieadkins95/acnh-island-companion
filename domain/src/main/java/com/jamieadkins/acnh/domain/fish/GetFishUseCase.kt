package com.jamieadkins.acnh.domain.fish

import com.jamieadkins.acnh.domain.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class GetFishUseCase @Inject constructor(
    private val fishRepository: FishRepository,
    private val schedulerProvider: SchedulerProvider
) {

    fun getFish(): Observable<List<FishEntity>> {
        return fishRepository.getFish()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}