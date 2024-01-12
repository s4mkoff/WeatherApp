package com.s4mkoff.weatherapp.features.weather.domain.repository

import com.s4mkoff.weatherapp.features.weather.domain.model.weather.WeatherModel

interface WeatherRepository {

    suspend fun getWeather(latitude: String, longitude: String): WeatherModel

}