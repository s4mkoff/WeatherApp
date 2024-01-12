package com.s4mkoff.weatherapp.features.weather.data.repository

import android.util.Log
import com.s4mkoff.weatherapp.features.weather.data.source.WeatherApi
import com.s4mkoff.weatherapp.features.weather.domain.model.weather.WeatherModel
import com.s4mkoff.weatherapp.features.weather.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi
): WeatherRepository {


    override suspend fun getWeather(latitude: String, longitude: String): WeatherModel {
        Log.v("weather in Api", "$latitude, $longitude")
        return api.getWeather(latitude, longitude)
    }
}