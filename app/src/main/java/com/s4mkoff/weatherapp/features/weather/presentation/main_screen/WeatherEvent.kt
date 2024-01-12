package com.s4mkoff.weatherapp.features.weather.presentation.main_screen

sealed class WeatherEvent {
    object LoadWeather: WeatherEvent()
    object CheckNetwork: WeatherEvent()

    object GetLocation: WeatherEvent()
}