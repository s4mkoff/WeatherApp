package com.s4mkoff.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.s4mkoff.weatherapp.features.weather.presentation.main_screen.MainScreen
import com.s4mkoff.weatherapp.features.weather.presentation.main_screen.WeatherViewModel
import com.s4mkoff.weatherapp.features.weather.presentation.main_screen.WeatherViewModelFactory
import com.s4mkoff.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = WeatherViewModelFactory().create(WeatherViewModel::class.java)
        requestPermission()
        WeatherApp
            .weatherModule
            .networkRepository
            .registerNetworkService(
                locationPermission = viewModel.state.value.isLocationPermissionGranted,
                getWeatherFunction = {
                    viewModel.getWeatherByLocation()
                }
            )
        setContent {
            WeatherAppTheme {
                MainScreen(
                    state = viewModel.state.value,
                    getWeatherByCity = {
                            city -> viewModel.getWeatherByLocation(city)
                    }
                )
            }
        }
    }

    private fun requestPermission() {
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted: Map<String, Boolean> ->
            viewModel.state.value.isLocationPermissionGranted = isGranted.containsValue(false)
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

}