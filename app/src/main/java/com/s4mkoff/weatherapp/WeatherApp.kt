package com.s4mkoff.weatherapp

import android.app.Application
import com.s4mkoff.weatherapp.di.WeatherModule
import com.s4mkoff.weatherapp.di.WeatherModuleImpl
import com.s4mkoff.weatherapp.features.weather.domain.util.PermissionStates
import com.s4mkoff.weatherapp.features.weather.presentation.util.LocationState
import com.s4mkoff.weatherapp.features.weather.presentation.util.NetworkState

class WeatherApp: Application() {
    companion object {
        lateinit var weatherModule: WeatherModule
        var networkState = NetworkState.UNAVAILABLE
        var locationState = LocationState.UNAVAILABLE
        val longitude = "47.8217792"
        val latitude = "35.0390402"
        var locationPermission = PermissionStates.LocationPermission()
    }

    override fun onCreate() {
        super.onCreate()
        weatherModule = WeatherModuleImpl(this)
    }
}