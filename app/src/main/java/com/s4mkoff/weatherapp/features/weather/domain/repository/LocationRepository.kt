package com.s4mkoff.weatherapp.features.weather.domain.repository

import android.location.Location

interface LocationRepository {

    suspend fun getLocation(): Location?

    fun updateLocation(onLocationResult: () -> Unit)
}