package com.s4mkoff.weatherapp.data

import com.s4mkoff.weatherapp.features.weather.domain.model.weather.WeatherModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("/v1/forecast?current=temperature_2m,relative_humidity_2m,is_day,weather_code,pressure_msl,surface_pressure,wind_speed_10m&daily=weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,daylight_duration,sunshine_duration&timeformat=unixtime&timezone=Europe%2FBerlin")
    suspend fun getWeather(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): WeatherModel
}

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.open-meteo.com/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

object WeatherApi {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}