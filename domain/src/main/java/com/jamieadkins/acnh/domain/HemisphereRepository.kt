package com.jamieadkins.acnh.domain

import io.reactivex.Observable

interface HemisphereRepository {

    fun observeHemisphere(): Observable<HemisphereEntity>

    fun setHemisphere(hemisphere: HemisphereEntity)
}