package com.s4mkoff.weatherapp

import android.app.Application
import com.s4mkoff.weatherapp.di.WeatherModule
import com.s4mkoff.weatherapp.di.WeatherModuleImpl
import com.s4mkoff.weatherapp.features.weather.domain.util.NetworkState

class WeatherApp: Application() {
    companion object {
        lateinit var weatherModule: WeatherModule
        var networkState = NetworkState.UNAVAILABLE
    }

    override fun onCreate() {
        super.onCreate()
        weatherModule = WeatherModuleImpl(this)
    }
}