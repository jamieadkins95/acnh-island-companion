package com.jamieadkins.acnh.domain.bugs

import com.jamieadkins.acnh.domain.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class ToggleBugsCaughtUseCase @Inject constructor(
    private val repository: BugRepository,
    private val schedulerProvider: SchedulerProvider
) {

    fun toggle(bug: BugEntity) = repository.toggleBugCaught(bug.id)
}