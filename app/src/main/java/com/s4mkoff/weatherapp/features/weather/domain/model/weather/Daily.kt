package com.s4mkoff.weatherapp.features.weather.domain.model.weather

data class Daily(
    val daylight_duration: List<Double>,
    val sunrise: List<Int>,
    val sunset: List<Int>,
    val sunshine_duration: List<Double>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val time: List<Int>,
    val weather_code: List<Int>
)