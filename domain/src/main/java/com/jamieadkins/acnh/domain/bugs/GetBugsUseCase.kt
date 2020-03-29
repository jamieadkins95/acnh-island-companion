package com.jamieadkins.acnh.domain.bugs

import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.domain.fish.FishRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetBugsUseCase @Inject constructor(
    private val bugRepository: BugRepository
) {

    fun getBugs(): Observable<List<BugEntity>> {
        return bugRepository.getBugs()
    }
}