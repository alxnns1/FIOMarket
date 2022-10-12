package com.alxnns1.utils

import java.util.Date
import javax.inject.Inject

class DateWrapper @Inject constructor() {

    fun newInstance(date: Long = -1) = if (date < 0) Date() else Date(date)
}