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

    fun isCritterGoingSoon(currentMonth: Int, months: List<Int>): Boolean {
        return when (currentMonth) {
            12 -> months.contains(currentMonth) && !months.contains(1)
            else -> months.contains(currentMonth) && !months.contains(currentMonth + 1)
        }
    }
}