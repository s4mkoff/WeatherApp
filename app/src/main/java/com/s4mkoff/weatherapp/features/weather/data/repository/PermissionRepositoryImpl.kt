package com.s4mkoff.weatherapp.features.weather.data.repository

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.s4mkoff.weatherapp.WeatherApp
import com.s4mkoff.weatherapp.features.weather.domain.repository.PermissionRepository

class PermissionRepositoryImpl(
    private val context: Context
): PermissionRepository {

    override fun locationAvailability(): Boolean {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return hasAccessFineLocationPermission || hasAccessCoarseLocationPermission || isGpsEnabled
    }


}