package com.pahnal.submissioncapstone.core.utils

import android.view.View
import kotlin.math.pow
import kotlin.math.roundToInt

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun Double.round(decimals: Int = 2): Double {
    val pow = 10.0.pow(decimals)
    return (this * pow).roundToInt() / pow
}
