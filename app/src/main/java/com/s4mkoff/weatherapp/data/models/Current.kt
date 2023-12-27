package com.s4mkoff.weatherapp.data.models

data class Current(
    val interval: Int,
    val is_day: Int,
    val pressure_msl: Double,
    val relative_humidity_2m: Int,
    val surface_pressure: Double,
    val temperature_2m: Double,
    val time: Int,
    val weather_code: Int,
    val wind_speed_10m: Double
)