package com.plcoding.core.presentation.ui

import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.time.Duration

fun Duration.formatted() : String {
    val totalSeconds = inWholeSeconds
    // "01", "10"
    // put the result of this calculation in here as a string formatting 0 leading if there is only one digit, otherwise two digits
    val hours = String.format("%02d", totalSeconds / 3600)
    val minutes = String.format("%02d", (totalSeconds % 3600) / 60)
    val seconds = String.format("%02d", totalSeconds % 60)
    return "${hours}:${minutes}:${seconds}"
}

fun Double.toFormattedKm(): String {
    return "${this.roundToDecimals(1)} km"
}

fun Duration.toFormattedPace(distanceKm: Double): String {
    if (this == Duration.ZERO || distanceKm < 0.0) {
        return  "-"
    }
    // how many seconds we needed per km
    val secondsPerKm = (this.inWholeSeconds / distanceKm).roundToInt()
    val avgPaceMinutes = secondsPerKm / 60
    val avgPaceSeconds = String.format("%02d", secondsPerKm % 60)
    return "$avgPaceMinutes:$avgPaceSeconds / km"
}

// 5.678
// factor = 10^1 = 10 -> pow function
// round(5.678 * factor) / factor --> 5.7
private fun Double.roundToDecimals(decimalCount: Int): Double {
    val factor = 10f.pow(decimalCount)
    return round(this * factor) / factor
}