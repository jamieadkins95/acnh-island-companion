package com.jamieadkins.acnh.domain.bugs

import io.reactivex.Observable

interface BugRepository {

    fun getBugs(): Observable<List<BugEntity>>

    fun getBug(id: String): Observable<BugEntity>
}