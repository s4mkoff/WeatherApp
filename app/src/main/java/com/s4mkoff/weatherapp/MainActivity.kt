package com.s4mkoff.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.s4mkoff.weatherapp.ui.screens.MainScreen
import com.s4mkoff.weatherapp.ui.theme.WeatherAppTheme
import com.s4mkoff.weatherapp.viewmodels.WeatherViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherViewModel = WeatherViewModel(this)
        weatherViewModel.checkInternetConnection()
        setContent {
            WeatherAppTheme {
                MainScreen(viewModel = weatherViewModel)
            }
        }
    }
}