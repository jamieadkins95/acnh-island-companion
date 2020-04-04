package com.jamieadkins.acnh.data.bugs

import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals

class BugRepositoryImplTest {

    @Test
    fun `Available all year, one range jan - dec`() {
        val allYear = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        val result = BugRepositoryImpl.getMonthRanges(allYear)
        assertEquals(listOf(1 to 12), result)
    }

    @Test
    fun `Available jan to feb, one range jan - feb`() {
        val months = listOf(1, 2)
        val result = BugRepositoryImpl.getMonthRanges(months)
        assertEquals(listOf(1 to 2), result)
    }

    @Test
    fun `Available dec to jan, one range dec - jan`() {
        val months = listOf(1, 12)
        val result = BugRepositoryImpl.getMonthRanges(months)
        assertEquals(listOf(12 to 1), result)
    }

    @Test
    fun `Available jan to feb and jun to jul, 2 ranges`() {
        val months = listOf(1, 2, 6, 7)
        val result = BugRepositoryImpl.getMonthRanges(months)
        assertEquals(listOf(1 to 2, 6 to 7), result)
    }

    @Test
    fun `Available nov to feb and jun to sept, 2 ranges`() {
        val months = listOf(1, 2, 6, 7, 8, 11, 12)
        val result = BugRepositoryImpl.getMonthRanges(months)
        assertEquals(listOf(11 to 2, 6 to 8), result)
    }

    @Test
    fun `Available jan only`() {
        val months = listOf(1)
        val result = BugRepositoryImpl.getMonthRanges(months)
        assertEquals(listOf(1 to 1), result)
    }

    @Test
    fun `Available dec only`() {
        val months = listOf(12)
        val result = BugRepositoryImpl.getMonthRanges(months)
        assertEquals(listOf(12 to 12), result)
    }
}