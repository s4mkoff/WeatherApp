package com.s4mkoff.weatherapp.features.weather.domain.repository

import com.s4mkoff.weatherapp.features.weather.domain.util.LoadingState

interface NetworkRepository {
    fun registerNetworkService(
        locationPermission: Boolean,
        getWeatherFunction: () -> Unit
    )
}