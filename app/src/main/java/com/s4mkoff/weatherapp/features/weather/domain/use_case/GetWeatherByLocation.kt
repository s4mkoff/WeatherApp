package com.s4mkoff.weatherapp.features.weather.domain.use_case

import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.s4mkoff.weatherapp.features.weather.domain.model.MyWeather
import com.s4mkoff.weatherapp.features.weather.domain.model.weather.WeatherModel
import com.s4mkoff.weatherapp.features.weather.domain.repository.LocationRepository
import com.s4mkoff.weatherapp.features.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GetWeatherByLocation(
    private val weatherRepo: WeatherRepository,
    private val locationRepo: LocationRepository,
    private val geocoder: Geocoder
) {
    suspend operator fun invoke(): MyWeather {
        val location = locationRepo.getLocation()
        val adress = if (location!=null) {
            geocoder.getFromLocation(location.latitude, location.longitude, 1)
        } else {
            null
        }
        return MyWeather(
            latitude = (location?.latitude ?: "").toString(),
            longitude = (location?.longitude ?: "").toString(),
            cityName = adress?.get(0)?.locality ?: "",
            locationName = adress?.get(0)?.countryName ?: "",
            weather = weatherRepo.getWeather(
                location?.latitude.toString(), location?.longitude.toString()
            )
        )

    }

}