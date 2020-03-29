package com.jamieadkins.acnh.domain

import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class CritterAvailabilityChecker @Inject constructor() {

    fun isCritterAvailableNow(now: ZonedDateTime, startHour: Int, endHour: Int, months: List<Int>): Boolean {
        val correctTime = if (startHour > endHour) {
            now.hour < startHour || now.hour > endHour // Night fish
        } else {
            now.hour > startHour && now.hour < endHour // Day fish
        }
        val correctMonth = months.contains(now.monthValue)
        return correctTime && correctMonth
    }
}