package com.alxnns1.utils

import java.text.NumberFormat
import javax.inject.Inject

class NumberFormatter @Inject constructor() {
    private val numberFormat by lazy { NumberFormat.getInstance() }

    fun format(number: Number): String {
        return numberFormat.format(number)
    }
}