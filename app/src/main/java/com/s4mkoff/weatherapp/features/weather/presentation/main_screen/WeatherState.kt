package com.s4mkoff.weatherapp.features.weather.presentation.main_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.s4mkoff.weatherapp.features.weather.domain.model.weather.WeatherModel
import com.s4mkoff.weatherapp.features.weather.domain.util.LoadingState

data class WeatherState(
    val weather: WeatherModel? = null,
    val loading: MutableState<LoadingState> = mutableStateOf(LoadingState.LOADING),
    val isNetworkAvailable: Boolean = false,
    var isLocationPermissionGranted: Boolean = false,
    var firstLoad: Boolean = true,
    val cityName: String = "",
    val countryName: String = "",
)
