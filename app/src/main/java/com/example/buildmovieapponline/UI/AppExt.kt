package com.example.buildmovieapponline.UI

 fun stringForTime(timeMs : Long): String {
    val totalSeconds = timeMs / 1000
    val seconds = totalSeconds % 60
    val minutes = (totalSeconds / 60) % 60
    val hours = totalSeconds / 3600

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}