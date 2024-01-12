package com.s4mkoff.weatherapp.features.weather.domain.use_case

import android.location.Location
import android.util.Log
import com.s4mkoff.weatherapp.features.weather.domain.model.weather.WeatherModel
import com.s4mkoff.weatherapp.features.weather.domain.repository.LocationRepository
import com.s4mkoff.weatherapp.features.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GetWeatherByLocation(
    private val weatherRepo: WeatherRepository,
    private val locationRepo: LocationRepository
) {
    suspend operator fun invoke(): WeatherModel? {
        val location = locationRepo.getLocation()
        return weatherRepo.getWeather(
            location?.latitude.toString(), location?.longitude.toString()
        )
    }

}