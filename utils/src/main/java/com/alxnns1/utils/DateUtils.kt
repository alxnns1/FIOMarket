package com.alxnns1.utils

import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DateUtils @Inject constructor(
    private val dateWrapper: DateWrapper
) {

    fun timeRemaining(later: Long): String {
        val now = dateWrapper.newInstance().time

        var millisRemaining = later - now

        val daysRemaining = TimeUnit.MILLISECONDS.toDays(millisRemaining)
        if (daysRemaining > 0) millisRemaining = millisRemaining.mod(daysRemaining)

        val hoursRemaining = TimeUnit.MILLISECONDS.toHours(millisRemaining)
        if (hoursRemaining > 0) millisRemaining = millisRemaining.mod(hoursRemaining)

        val minutesRemaining = TimeUnit.MILLISECONDS.toMinutes(millisRemaining)

        val output =  if (daysRemaining > 0) {
            "$daysRemaining day" + if (daysRemaining != 1L) "s" else ""
        } else if (hoursRemaining > 0) {
            "$hoursRemaining hour" + if (hoursRemaining != 1L) "s" else ""
        } else {
            "$minutesRemaining minute" + if (minutesRemaining != 1L) "s" else ""
        }

        return output
    }
}