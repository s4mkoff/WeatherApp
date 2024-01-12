package com.s4mkoff.weatherapp.features.weather.domain.repository

interface PermissionRepository {
    fun locationAvailability(): Boolean

}