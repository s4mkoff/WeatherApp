package com.s4mkoff.weatherapp.features.weather.domain.repository

import android.app.Application
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient

interface LocationRepository {

    suspend fun getLocation(): Location?

}