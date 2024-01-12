package com.s4mkoff.weatherapp.features.weather.domain.use_case

data class WeatherUseCases(
    val getWeatherByLocationUseCase: GetWeatherByLocation,
    val getWeatherByCity: GetWeatherByCity
)