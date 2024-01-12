package com.s4mkoff.weatherapp.features.weather.domain.use_case

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.s4mkoff.weatherapp.data.WeatherApi
import com.s4mkoff.weatherapp.features.weather.domain.model.MyWeather
import kotlinx.coroutines.launch

class GetWeatherByCity(
    private val geocoder: Geocoder
) {
    suspend operator fun invoke(city: String): MyWeather? {
        val adress: MutableList<Address> = geocoder.getFromLocationName(city, 1) ?: return null
        if (adress.size == 0) return null
        val weather = WeatherApi.retrofitService.getWeather(
            adress[0].latitude.toString(),
            adress[0].longitude.toString()
        )
        return MyWeather(
            latitude = adress[0].latitude.toString(),
            longitude = adress[0].longitude.toString(),
            cityName = adress[0].locality,
            locationName = adress[0].countryName,
            weather = weather
        )
    }
}