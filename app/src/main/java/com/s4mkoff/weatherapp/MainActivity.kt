package com.s4mkoff.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.s4mkoff.weatherapp.features.weather.presentation.main_screen.MainScreen
import com.s4mkoff.weatherapp.features.weather.presentation.main_screen.WeatherViewModel
import com.s4mkoff.weatherapp.features.weather.presentation.util.LoadingState
import com.s4mkoff.weatherapp.features.weather.presentation.util.NetworkState
import com.s4mkoff.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = WeatherViewModelFactory().create(WeatherViewModel::class.java)
        requestPermission()
        connectivity()
        setContent {
            WeatherAppTheme {
                MainScreen(
                    state = viewModel.state.value,
                    getWeatherByCity = {
                            city -> viewModel.getWeatherByCity(city)
                    }
                )
            }
        }
    }

    private fun requestPermission() {
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted: Map<String, Boolean> ->
            if (!isGranted.containsValue(false)) {
                viewModel.state.value.isLocationPermissionGranted = false
            } else {
                viewModel.state.value.isLocationPermissionGranted = true
            }
        }
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.state.value.isLocationPermissionGranted = true
            }
            else -> {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

//    @SuppressLint("MissingPermission")
//    fun requestUpdates(
//    ) {
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(p0: LocationResult) {
//                Log.v("Locality", "${p0.lastLocation}")
//                if (p0.lastLocation!=null) {
//                    viewModel.getWeatherByLocation()
//                    locationClient.removeLocationUpdates(locationCallback)
//                }
//            }
//        }
//        val locationRequest = LocationRequest().setFastestInterval(0).setMaxWaitTime(0).setNumUpdates(1).setSmallestDisplacement(0f).setPriority(Priority.PRIORITY_HIGH_ACCURACY)
//        if (locationAvailability()) {
//            locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
//            return
//        }
//    }

    fun locationAvailability(): Boolean {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return hasAccessFineLocationPermission || hasAccessCoarseLocationPermission || isGpsEnabled
    }

    private fun connectivity() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        val networkRequest: NetworkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                WeatherApp.networkState = NetworkState.AVAILABLE
                if (viewModel.state.value.loading != LoadingState.SUCCESS
                    && viewModel.state.value.isLocationPermissionGranted) {
                    viewModel.getWeatherByLocation()
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

class WeatherViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(
            WeatherApp.weatherModule.weatherUseCase
        ) as T
    }
}