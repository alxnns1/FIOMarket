package com.alxnns1.utils

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

private const val dayInMillis = 1000L*60*60*24
private const val hourInMillis = 1000L*60*60
private const val minuteInMillis = 1000L*60

class DateUtilsTest {

    @Test
    fun `timeRemaining with multiple days later time returns days`() {
        val dateWrapper = mockk<DateWrapper> {
            every { newInstance().time } returns 0
        }

        val subject = DateUtils(dateWrapper)

        val actual = subject.timeRemaining(dayInMillis * 2)

        assertEquals("2 days", actual)
    }

    @Test
    fun `timeRemaining with one day later time returns day`() {
        val dateWrapper = mockk<DateWrapper> {
            every { newInstance().time } returns 0
        }

        val subject = DateUtils(dateWrapper)

        val actual = subject.timeRemaining(dayInMillis)

        assertEquals("1 day", actual)
    }

    @Test
    fun `timeRemaining with multiple hours later time returns hours`() {
        val dateWrapper = mockk<DateWrapper> {
            every { newInstance().time } returns 0
        }

        val subject = DateUtils(dateWrapper)

        val actual = subject.timeRemaining(hourInMillis * 2)

        assertEquals("2 hours", actual)
    }

    @Test
    fun `timeRemaining with one hour later time returns hour`() {
        val dateWrapper = mockk<DateWrapper> {
            every { newInstance().time } returns 0
        }

        val subject = DateUtils(dateWrapper)

        val actual = subject.timeRemaining(hourInMillis)

        assertEquals("1 hour", actual)
    }

    @Test
    fun `timeRemaining with multiple minutes later time returns minutes`() {
        val dateWrapper = mockk<DateWrapper> {
            every { newInstance().time } returns 0
        }

        val subject = DateUtils(dateWrapper)

        val actual = subject.timeRemaining(minuteInMillis * 2)

        assertEquals("2 minutes", actual)
    }

    @Test
    fun `timeRemaining with one minute later time returns minute`() {
        val dateWrapper = mockk<DateWrapper> {
            every { newInstance().time } returns 0
        }

        val subject = DateUtils(dateWrapper)

        val actual = subject.timeRemaining(minuteInMillis)

        assertEquals("1 minute", actual)
    }
}