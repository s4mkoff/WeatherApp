package com.s4mkoff.weatherapp.features.weather.data.repository

import com.s4mkoff.weatherapp.features.weather.data.source.WeatherApi
import com.s4mkoff.weatherapp.features.weather.domain.model.weather.WeatherModel
import com.s4mkoff.weatherapp.features.weather.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getWeather(latitude: String, longitude: String): WeatherModel? {
        return try {
            api.getWeather(latitude, longitude)
        } catch (e: Exception) {
            return null
        }

    }
}