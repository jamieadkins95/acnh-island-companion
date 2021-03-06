package com.jamieadkins.acnh.domain

import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.domain.fish.FishEntity
import org.threeten.bp.ZonedDateTime

data class BugFishSummaryEntity(
    val fish: List<FishEntity>,
    val bugs: List<BugEntity>,
    val timeEvaluatedAt: ZonedDateTime
) {

    val uncaughtFish = fish.filter { it.caught.not() }
    val uncaughtBugs = bugs.filter { it.caught.not() }
    val alreadyCaught = fish.count { it.caught } + bugs.count { it.caught }
}