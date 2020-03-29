package com.jamieadkins.acnh.domain.bugs

import io.reactivex.Observable

interface BugRepository {

    fun getBugs(): Observable<List<BugEntity>>
}