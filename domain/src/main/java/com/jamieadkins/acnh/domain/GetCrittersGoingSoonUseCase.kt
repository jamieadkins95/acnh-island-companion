package com.jamieadkins.acnh.domain

import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.domain.bugs.BugRepository
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.domain.fish.FishRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class GetCrittersGoingSoonUseCase @Inject constructor(
    private val fishRepository: FishRepository,
    private val bugRepository: BugRepository,
    private val critterAvailabilityChecker: CritterAvailabilityChecker,
    private val schedulerProvider: SchedulerProvider
) {

    fun getCrittersGoingSoon(): Observable<BugFishSummaryEntity> {
        return Observable.combineLatest(
            fishRepository.getFish(),
            bugRepository.getBugs(),
            BiFunction { allFish: List<FishEntity>, allBugs: List<BugEntity> ->
                val now = ZonedDateTime.now()
                val currentMonth = now.monthValue
                val fishGoingSoon = allFish.filter {
                    fish -> critterAvailabilityChecker.isCritterGoingSoon(currentMonth, fish.months)
                }
                val bugsGoingSoon = allBugs.filter {
                    bug -> critterAvailabilityChecker.isCritterGoingSoon(currentMonth, bug.months)
                }
                BugFishSummaryEntity(fishGoingSoon, bugsGoingSoon, now)
            }
        )
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}