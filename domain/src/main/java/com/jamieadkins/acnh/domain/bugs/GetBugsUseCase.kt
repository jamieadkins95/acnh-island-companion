package com.jamieadkins.acnh.domain.bugs

import com.jamieadkins.acnh.domain.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class GetBugsUseCase @Inject constructor(
    private val bugRepository: BugRepository,
    private val schedulerProvider: SchedulerProvider
) {

    fun getBugs(): Observable<List<BugEntity>> {
        return bugRepository.getBugs()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}