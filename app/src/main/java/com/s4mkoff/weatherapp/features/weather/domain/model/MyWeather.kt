package com.s4mkoff.weatherapp.features.weather.domain.model

import com.s4mkoff.weatherapp.features.weather.domain.model.weather.WeatherModel

data class MyWeather(
    val latitude: String,
    val longitude: String,
    val cityName: String,
    val locationName: String,
    val weather: WeatherModel?
)