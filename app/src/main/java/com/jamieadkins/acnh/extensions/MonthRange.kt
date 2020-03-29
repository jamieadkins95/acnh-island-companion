package com.jamieadkins.acnh.extensions

import android.content.res.Resources
import com.jamieadkins.acnh.R

fun Resources.getMonthRange(months: List<Int>): String {
    val monthArray = getStringArray(R.array.months)
    val startMonth = monthArray[months.firstOrNull()?.minus(1) ?: 0]
    val endMonth = monthArray[months.lastOrNull()?.minus(1) ?: 0]
    return getString(R.string.monthRange, startMonth, endMonth)
}