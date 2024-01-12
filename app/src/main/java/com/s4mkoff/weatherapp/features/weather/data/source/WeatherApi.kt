package com.s4mkoff.weatherapp.features.weather.data.source

import com.s4mkoff.weatherapp.features.weather.domain.model.weather.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/forecast?current=temperature_2m,relative_humidity_2m,is_day,weather_code,pressure_msl,surface_pressure,wind_speed_10m&daily=weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,daylight_duration,sunshine_duration&timeformat=unixtime&timezone=Europe%2FBerlin")
    suspend fun getWeather(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): WeatherModel
}