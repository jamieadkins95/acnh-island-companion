package com.jamieadkins.acnh.domain

import org.junit.Test

import org.junit.Assert.*

class CritterAvailabilityCheckerTest {

    private val critterAvailabilityChecker = CritterAvailabilityChecker()

    @Test
    fun `Critter Available this month and next, not going away soon`() {
        val result = critterAvailabilityChecker.isCritterGoingSoon(1, listOf(1, 2))
        assertEquals(false, result)
    }

    @Test
    fun `Critter Available not available this month or next, not going away soon`() {
        val result = critterAvailabilityChecker.isCritterGoingSoon(5, listOf(1, 2))
        assertEquals(false, result)
    }

    @Test
    fun `Critter Available available this month but not next, is going away soon`() {
        val result = critterAvailabilityChecker.isCritterGoingSoon(2, listOf(1, 2))
        assertEquals(true, result)
    }

    @Test
    fun `Critter Available available Dec and Jan, is not going away soon`() {
        val result = critterAvailabilityChecker.isCritterGoingSoon(12, listOf(1, 12))
        assertEquals(false, result)
    }

    @Test
    fun `Critter Available available Dec but not Jan, is going away soon`() {
        val result = critterAvailabilityChecker.isCritterGoingSoon(12, listOf(12))
        assertEquals(true, result)
    }
}