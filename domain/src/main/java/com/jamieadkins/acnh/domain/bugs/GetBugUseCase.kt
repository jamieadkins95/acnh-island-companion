package com.jamieadkins.acnh.domain.bugs

import com.jamieadkins.acnh.domain.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class GetBugUseCase @Inject constructor(
    private val bugRepository: BugRepository,
    private val schedulerProvider: SchedulerProvider
) {

    fun getBug(id: String): Observable<BugEntity> {
        return bugRepository.getBug(id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}