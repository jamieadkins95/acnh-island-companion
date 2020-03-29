package com.jamieadkins.acnh.domain

import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.domain.bugs.BugRepository
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.domain.fish.FishRepository
import io.reactivex.Observable
import io.reactivex.functions.Function3
import org.threeten.bp.ZonedDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetCrittersAvailableNowUseCase @Inject constructor(
    private val fishRepository: FishRepository,
    private val bugRepository: BugRepository,
    private val critterAvailabilityChecker: CritterAvailabilityChecker,
    private val schedulerProvider: SchedulerProvider
) {

    fun getCrittersAvailableNow(): Observable<AvailableNowEntity> {
        return Observable.combineLatest(
            Observable.interval(0, 1, TimeUnit.MINUTES),
            fishRepository.getFish(),
            bugRepository.getBugs(),
            Function3 { _: Long, allFish: List<FishEntity>, allBugs: List<BugEntity> ->
                val now = ZonedDateTime.now()
                val fishAvailableNow = allFish.filter {
                    fish -> critterAvailabilityChecker.isCritterAvailableNow(now, fish.startHour, fish.endHour, fish.months)
                }
                val bugsAvailableNow = allBugs.filter {
                    bug -> critterAvailabilityChecker.isCritterAvailableNow(now, bug.startHour, bug.endHour, bug.months)
                }
                AvailableNowEntity(fishAvailableNow, bugsAvailableNow, now)
            }
        )
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}