package com.s4mkoff.weatherapp.features.weather.domain.util

sealed class PermissionStates {
    data class NetworkPermission(var haveAccess: Boolean = false) : PermissionStates()
    data class LocationPermission(var haveAccess: Boolean = false) : PermissionStates()
}