package com.s4mkoff.weatherapp.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s4mkoff.weatherapp.data.WeatherApi
import com.s4mkoff.weatherapp.data.models.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel(context: Context): ViewModel() {

    var weather: WeatherModel? by mutableStateOf(null)
    var locality by mutableStateOf("")
    var countryName by mutableStateOf("")
    var isNetworkAviable by mutableStateOf(false)
    var isWeatherLoaded by mutableStateOf(false)
    var doFirstSearch by mutableStateOf(false)
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("weatherPrefs", Context.MODE_PRIVATE)
    private var lastlatitude = sharedPreferences.getString("latitude", "")
    private var lastlongitude = sharedPreferences.getString("longitude", "")
    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager

    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            isNetworkAviable = true
            if (!isWeatherLoaded) {
                getWeather()
            }
            super.onAvailable(network)
        }

        override fun onLost(network: Network) {
            isNetworkAviable = false
            super.onLost(network)
        }
    }

    private fun saveLocation(
        long: String,
        lat: String,
        locality: String,
        countryName: String
    ) {
        sharedPreferences.edit()
            .putString("latitude", lat)
            .putString("longitude", long)
            .putString("locality", locality)
            .putString("countryName", countryName)
            .apply()
    }

    fun getWeatherByCity(city: String, ctx: Context) {
        if (isNetworkAviable) {
            val geocoder = Geocoder(ctx)
            viewModelScope.launch {
                val adress: MutableList<Address>? = geocoder.getFromLocationName(city, 1)
                if (adress!=null) {
                    if (adress.size!=0) {
                        locality = adress[0].locality ?: ""
                        countryName = adress[0].countryName ?: ""
                        if (locality=="null") locality = ""
                        if (countryName=="null") countryName = ""
                        lastlatitude = adress[0].latitude.toString()
                        lastlongitude = adress[0].longitude.toString()
                        saveLocation(
                            lat = lastlatitude.toString(),
                            long = lastlongitude.toString(),
                            locality = locality,
                            countryName = countryName)
                        doFirstSearch = false
                        weather = WeatherApi.retrofitService.getWeather(adress[0].latitude.toString(), adress[0].longitude.toString())
                    }
                }
            }
        } else {
            Toast.makeText(ctx, "Turn on internet!", Toast.LENGTH_SHORT).show()
        }
    }

    fun getWeather() {
        viewModelScope.launch {
            if (lastlatitude!=""&&lastlongitude!="") {
                if (isNetworkAviable) {
                    locality = sharedPreferences.getString("locality", "") ?: ""
                    countryName = sharedPreferences.getString("countryName", "") ?: ""
                    weather = WeatherApi.retrofitService.getWeather("$lastlatitude", "$lastlongitude")
                    isWeatherLoaded = true
                }
            } else {
                doFirstSearch = true
            }
        }
    }

    fun checkInternetConnection() {
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}