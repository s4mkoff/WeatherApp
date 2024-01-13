package com.s4mkoff.weatherapp.features.weather.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import com.s4mkoff.weatherapp.WeatherApp
import com.s4mkoff.weatherapp.features.weather.domain.repository.NetworkRepository
import com.s4mkoff.weatherapp.features.weather.domain.util.LoadingState
import com.s4mkoff.weatherapp.features.weather.domain.util.NetworkState

class NetworkRepositoryImpl(
    private val context: Context
): NetworkRepository {
    override fun registerNetworkService(
        locationPermission: Boolean,
        getWeatherFunction: () -> Unit
    ) {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        val networkRequest: NetworkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        var isLoaded = false
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.v("NetworkStatus", "Available")
                WeatherApp.networkState = NetworkState.AVAILABLE
                if (!isLoaded && locationPermission) {
                    Log.v("NetworkStatus", "AvailableAndPermissionGranted")
                    WeatherApp.weatherModule.locationRepository.updateLocation {
                        getWeatherFunction()
                    }
                    isLoaded = true
                }
                super.onAvailable(network)
            }

            override fun onLost(network: Network) {
                WeatherApp.networkState = NetworkState.UNAVAILABLE
                super.onLost(network)
            }
        }
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}