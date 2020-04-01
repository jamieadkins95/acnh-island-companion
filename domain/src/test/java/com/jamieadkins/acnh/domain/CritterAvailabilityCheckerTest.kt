package com.jamieadkins.acnh.domain

import org.junit.Test

import org.junit.Assert.*

class CritterAvailabilityCheckerTest {

    private val critterAvailabilityChecker = CritterAvailabilityChecker()

    @Test
    fun `Critter available this month and next, not going away soon`() {
        val result = critterAvailabilityChecker.isCritterGoingSoon(1, listOf(1, 2))
        assertEquals(false, result)
    }

    @Test
    fun `Critter not available this month or next, not going away soon`() {
        val result = critterAvailabilityChecker.isCritterGoingSoon(5, listOf(1, 2))
        assertEquals(false, result)
    }

    @Test
    fun `Critter available this month but not next, is going away soon`() {
        val result = critterAvailabilityChecker.isCritterGoingSoon(2, listOf(1, 2))
        assertEquals(true, result)
    }

    @Test
    fun `Critter available Dec and Jan, is not going away soon`() {
        val result = critterAvailabilityChecker.isCritterGoingSoon(12, listOf(1, 12))
        assertEquals(false, result)
    }

    @Test
    fun `Critter available Dec but not Jan, is going away soon`() {
        val result = critterAvailabilityChecker.isCritterGoingSoon(12, listOf(12))
        assertEquals(true, result)
    }

    @Test
    fun `Critter available this month and next, not coming soon`() {
        val result = critterAvailabilityChecker.isCritterComingSoon(1, listOf(1, 2))
        assertEquals(false, result)
    }

    @Test
    fun `Critter not available this month or next, not coming soon`() {
        val result = critterAvailabilityChecker.isCritterComingSoon(5, listOf(1, 2))
        assertEquals(false, result)
    }

    @Test
    fun `Critter available this month but not next, is not coming soon`() {
        val result = critterAvailabilityChecker.isCritterComingSoon(2, listOf(1, 2))
        assertEquals(false, result)
    }

    @Test
    fun `Critter not available this month but is available next month, is coming soon`() {
        val result = critterAvailabilityChecker.isCritterComingSoon(2, listOf(3, 4))
        assertEquals(true, result)
    }

    @Test
    fun `Critter available Dec and Jan, is not coming soon`() {
        val result = critterAvailabilityChecker.isCritterComingSoon(12, listOf(1, 12))
        assertEquals(false, result)
    }

    @Test
    fun `Critter not available Dec but is available Jan, is coming soon`() {
        val result = critterAvailabilityChecker.isCritterComingSoon(12, listOf(1, 2))
        assertEquals(true, result)
    }

    @Test
    fun `Critter available this month and last, not new`() {
        val result = critterAvailabilityChecker.isCritterNewThisMonth(2, listOf(1, 2))
        assertEquals(false, result)
    }

    @Test
    fun `Critter not available this month or next, not new`() {
        val result = critterAvailabilityChecker.isCritterNewThisMonth(5, listOf(1, 2))
        assertEquals(false, result)
    }

    @Test
    fun `Critter available this month but not last, is new`() {
        val result = critterAvailabilityChecker.isCritterNewThisMonth(2, listOf(2, 3))
        assertEquals(true, result)
    }

    @Test
    fun `Critter available Dec and Jan, is not new`() {
        val result = critterAvailabilityChecker.isCritterNewThisMonth(1, listOf(1, 12))
        assertEquals(false, result)
    }

    @Test
    fun `Critter not available Dec but is available Jan, is new`() {
        val result = critterAvailabilityChecker.isCritterNewThisMonth(1, listOf(1, 2))
        assertEquals(true, result)
    }
}