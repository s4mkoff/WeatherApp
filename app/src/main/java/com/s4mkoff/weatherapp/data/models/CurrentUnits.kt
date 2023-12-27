package com.s4mkoff.weatherapp.data.models

data class CurrentUnits(
    val interval: String,
    val is_day: String,
    val pressure_msl: String,
    val relative_humidity_2m: String,
    val surface_pressure: String,
    val temperature_2m: String,
    val time: String,
    val weather_code: String,
    val wind_speed_10m: String
)