package com.s4mkoff.weatherapp.features.weather.domain.model.weather

data class DailyUnits(
    val daylight_duration: String,
    val sunrise: String,
    val sunset: String,
    val sunshine_duration: String,
    val temperature_2m_max: String,
    val temperature_2m_min: String,
    val time: String,
    val weather_code: String
)