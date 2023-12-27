package com.s4mkoff.weatherapp.data

import com.s4mkoff.weatherapp.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class WeatherCodeHelpers {
    fun weatherCodeToResId(
        weatherCode: Int,
        isDay: Int
    ): Int {
        if (isDay==1) {
            return when (weatherCode) {
                0, 1 -> R.drawable.day113
                2 -> R.drawable.day116
                3 -> R.drawable.day122
                45, 48 -> R.drawable.day248
                51, 53 -> R.drawable.day263
                55 -> R.drawable.day266
                56 -> R.drawable.day281
                57 -> R.drawable.day284
                61 -> R.drawable.day296
                63 -> R.drawable.day302
                65 -> R.drawable.day308
                66 -> R.drawable.day311
                67 -> R.drawable.day314
                71 -> R.drawable.day326
                73 -> R.drawable.day332
                75 -> R.drawable.day338
                77 -> R.drawable.day350
                80 -> R.drawable.day353
                81, 82 -> R.drawable.day356
                85 -> R.drawable.day368
                86 -> R.drawable.day371
                96 -> R.drawable.day386
                99 -> R.drawable.day389
                else -> R.drawable.day200
            }
        } else {
            return when (weatherCode) {
                0, 1 -> R.drawable.night113
                2 -> R.drawable.night116
                3 -> R.drawable.night122
                45, 48 -> R.drawable.night248
                51, 53 -> R.drawable.night263
                55 -> R.drawable.night266
                56 -> R.drawable.night281
                57 -> R.drawable.night284
                61 -> R.drawable.night296
                63 -> R.drawable.night302
                65 -> R.drawable.night308
                66 -> R.drawable.night311
                67 -> R.drawable.night314
                71 -> R.drawable.night326
                73 -> R.drawable.night332
                75 -> R.drawable.night338
                77 -> R.drawable.night350
                80 -> R.drawable.night353
                81, 82 -> R.drawable.night356
                85 -> R.drawable.night368
                86 -> R.drawable.night371
                96 -> R.drawable.night386
                99 -> R.drawable.night389
                else -> R.drawable.night200
            }
        }
    }

    fun weatherCodeToString(
        weatherCode: Int
    ): String {
        return when (weatherCode) {
            1 -> "Mainly clear"
            2 -> "Partly cloudy"
            3 -> "Overcast"
            45, 48 -> "Fog"
            51, 53, 55 -> "Drizzle"
            56 -> "Freezing drizzle"
            61, 63, 65 -> "Rain"
            66, 67 -> "Freezing rain"
            71, 73, 75 -> "Snow"
            77 -> "Snow grains"
            80, 81, 82 -> "Rain shower"
            85, 86 -> "Snow shower"
            95, 96, 99 -> "Thunderstorm"
            else -> "Clear sky"
        }
    }

    fun formatTime(
        time: Long,
        condition: String
    ): String {
        val formatter = when (condition) {
            "General" -> DateTimeFormatter.ofPattern("eeee, d MMM y | h:mma")
            "Sun" -> DateTimeFormatter.ofPattern("h:ma")
            else -> DateTimeFormatter.ofPattern("eee, d")
        }
        return formatter.format(LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault()))
    }

    fun formatSecondsToHoursMinutes(seconds: Double): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        return "${hours.toInt()}h ${minutes.toInt()}m"
    }
}