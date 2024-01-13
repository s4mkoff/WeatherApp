package com.s4mkoff.weatherapp.di

import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.s4mkoff.weatherapp.features.weather.data.repository.LocationRepositoryImpl
import com.s4mkoff.weatherapp.features.weather.data.repository.WeatherRepositoryImpl
import com.s4mkoff.weatherapp.features.weather.data.source.WeatherApi
import com.s4mkoff.weatherapp.features.weather.domain.repository.LocationRepository
import com.s4mkoff.weatherapp.features.weather.domain.repository.WeatherRepository
import com.s4mkoff.weatherapp.features.weather.domain.use_case.GetWeatherByCity
import com.s4mkoff.weatherapp.features.weather.domain.use_case.GetWeatherByLocation
import com.s4mkoff.weatherapp.features.weather.domain.use_case.WeatherUseCases
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface WeatherModule{
    val weatherApi: WeatherApi
    val weatherUseCase: WeatherUseCases
    val weatherRepository: WeatherRepository
    val connectivityManager: ConnectivityManager
    val locationRepository: LocationRepository
    val geocoder: Geocoder
    val fusedLocalManager: FusedLocationProviderClient
}

class WeatherModuleImpl(
    appContext: Context
): WeatherModule {
    override val weatherApi: WeatherApi by lazy {
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(WeatherApi::class.java)
    }
    override val connectivityManager = appContext.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    override val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            weatherApi,
            )
    }
    override val fusedLocalManager = LocationServices.getFusedLocationProviderClient(appContext)
    override val locationRepository: LocationRepository by lazy {
        LocationRepositoryImpl(
            fusedLocalManager,
            appContext
        )
    }
    override val geocoder: Geocoder by lazy {
        Geocoder(appContext)
    }
    override val weatherUseCase: WeatherUseCases by lazy {
        WeatherUseCases(
            GetWeatherByLocation(weatherRepository, locationRepository, geocoder),
            GetWeatherByCity(geocoder)
        )
    }
}