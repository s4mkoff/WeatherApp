package com.s4mkoff.weatherapp.features.weather.presentation.main_screen

import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s4mkoff.weatherapp.features.weather.domain.model.MyWeather
import com.s4mkoff.weatherapp.features.weather.domain.use_case.WeatherUseCases
import com.s4mkoff.weatherapp.features.weather.presentation.util.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherUseCase: WeatherUseCases
): ViewModel() {

    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state

    private var getWeatherJob: Job? = null
    fun getWeatherByLocation() {
        getWeatherJob?.cancel()
        _state.value = state.value.copy(
            loading = LoadingState.LOADING
        )
        getWeatherJob = CoroutineScope(Dispatchers.IO).launch {
            weatherUseCase.getWeatherByLocationUseCase().apply {
                _state.value = state.value.copy(
                    weather = this.weather,
                    loading = if (this!=null) LoadingState.SUCCESS else LoadingState.ERROR,
                    cityName = this.cityName,
                    countryName = this.locationName
                )
            }
        }
    }

    fun getWeatherByCity(
        city: String
    ) {
        getWeatherJob?.cancel()
        _state.value = state.value.copy(
            loading = LoadingState.LOADING
        )
        getWeatherJob = viewModelScope.launch {
            weatherUseCase.getWeatherByCity(city).apply {
                _state.value = state.value.copy(
                    weather = this?.weather,
                    loading = if (this?.weather !=null) LoadingState.SUCCESS else LoadingState.ERROR,
                    cityName = this?.cityName ?: "",
                    countryName = this?.locationName ?: ""
                )
            }
        }
    }
}