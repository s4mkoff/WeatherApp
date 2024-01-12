package com.s4mkoff.weatherapp.features.weather.presentation.main_screen

import com.s4mkoff.weatherapp.features.weather.domain.model.weather.WeatherModel
import com.s4mkoff.weatherapp.features.weather.presentation.util.LoadingState

data class WeatherState(
    val weather: WeatherModel? = null,
    val loading: LoadingState = LoadingState.LOADING,
    val isNetworkAvailable: Boolean = false,
    val isLocationAvailable: Boolean = false,
    val cityName: String = "",
    val countryName: String = "",
)
