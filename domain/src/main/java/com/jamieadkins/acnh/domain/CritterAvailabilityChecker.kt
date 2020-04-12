package com.jamieadkins.acnh.domain

import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class CritterAvailabilityChecker @Inject constructor() {

    fun isCritterAvailableNow(now: ZonedDateTime, startHour: Int, endHour: Int, months: List<Int>): Boolean {
        val currentHour = now.hour
        val correctTime = if (startHour > endHour) {
            currentHour < endHour || currentHour > startHour // Night fish
        } else {
            currentHour > startHour && currentHour < endHour // Day fish
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

    fun isCritterComingSoon(currentMonth: Int, months: List<Int>): Boolean {
        return when (currentMonth) {
            12 -> !months.contains(currentMonth) && months.contains(1)
            else -> !months.contains(currentMonth) && months.contains(currentMonth + 1)
        }
    }

    fun isCritterNewThisMonth(currentMonth: Int, months: List<Int>): Boolean {
        return when (currentMonth) {
            1 -> months.contains(currentMonth) && !months.contains(12)
            else -> months.contains(currentMonth) && !months.contains(currentMonth - 1)
        }
    }
}