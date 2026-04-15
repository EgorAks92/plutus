package com.example.familyfinance.presentation.common

import java.util.Calendar

fun currentMonthRange(): Pair<Long, Long> {
    val start = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    val end = start.clone() as Calendar
    end.add(Calendar.MONTH, 1)
    end.add(Calendar.MILLISECOND, -1)
    return start.timeInMillis to end.timeInMillis
}
