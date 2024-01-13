package com.s4mkoff.weatherapp.features.weather.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.s4mkoff.weatherapp.WeatherApp
import com.s4mkoff.weatherapp.features.weather.domain.use_case.WeatherUseCases
import com.s4mkoff.weatherapp.features.weather.domain.util.LoadingState
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
    fun getWeatherByLocation(
        city: String = ""
    ) {
        getWeatherJob?.cancel()
        _state.value = state.value.copy(
            loading = mutableStateOf(LoadingState.LOADING),
            firstLoad = false
        )
        getWeatherJob = CoroutineScope(Dispatchers.IO).launch {
            weatherUseCase.getWeatherUseCase(city).apply {
                if (this.weather!=null) {
                    _state.value = state.value.copy(
                        weather = this.weather,
                        loading = mutableStateOf(LoadingState.SUCCESS),
                        cityName = this.cityName,
                        countryName = this.locationName
                    )
                } else {
                    _state.value = state.value.copy(
                        weather = state.value.weather,
                        loading = mutableStateOf(LoadingState.ERROR),
                        cityName = state.value.cityName,
                        countryName = state.value.countryName
                    )
                }
            }
        }
    }
}

class WeatherViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(
            WeatherApp.weatherModule.weatherUseCase
        ) as T
    }
}