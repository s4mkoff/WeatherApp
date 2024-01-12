package com.s4mkoff.weatherapp.features.weather.domain.use_case

import android.location.Location
import android.util.Log
import com.s4mkoff.weatherapp.features.weather.domain.model.weather.WeatherModel
import com.s4mkoff.weatherapp.features.weather.domain.repository.LocationRepository
import com.s4mkoff.weatherapp.features.weather.domain.repository.WeatherRepository

class GetWeatherByLocation(
    private val weatherRepo: WeatherRepository,
    private val locationRepo: LocationRepository
) {
    suspend operator fun invoke(): WeatherModel? {

        locationRepo.getLocation().let {location ->
            Log.v("Locality in useCase", "${location?.longitude}, ${location?.latitude}")
            return weatherRepo.getWeather(location?.latitude.toString(), location?.longitude.toString())
        }
    }

}