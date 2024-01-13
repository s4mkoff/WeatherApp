package com.s4mkoff.weatherapp.features.weather.domain.use_case

import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.s4mkoff.weatherapp.features.weather.domain.model.MyWeather
import com.s4mkoff.weatherapp.features.weather.domain.repository.LocationRepository
import com.s4mkoff.weatherapp.features.weather.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val weatherRepo: WeatherRepository,
    private val locationRepo: LocationRepository,
    private val geocoder: Geocoder
) {
    suspend operator fun invoke(city: String = ""): MyWeather {
        Log.v("Failure", "GetWeatherUseCase")
        val adress: MutableList<Address>? = if (city == "") {
            val location = locationRepo.getLocation()
            if (location != null) {
                geocoder.getFromLocation(location.latitude, location.longitude, 1)
            } else {
                null
            }
        } else {
            geocoder.getFromLocationName(city, 1)
        }
        val latitude = if (adress?.size != 0) {
            adress?.get(0)?.latitude ?: ""
        } else ""
        val longitude = if (adress?.size != 0) {
            adress?.get(0)?.longitude ?: ""
        } else ""
        val cityName = if (adress?.size != 0) {
            adress?.get(0)?.locality ?: ""
        } else {
            ""
        }
        val locationName = if (adress?.size != 0) {
            adress?.get(0)?.countryName ?: ""
        } else {
            ""
        }
        val weather = if (adress != null) {
            weatherRepo.getWeather(latitude.toString(), longitude.toString())
        } else {
            null
        }
        return MyWeather(
            latitude = latitude.toString(),
            longitude = longitude.toString(),
            cityName = cityName,
            locationName = locationName,
            weather = weather
        )

    }

}