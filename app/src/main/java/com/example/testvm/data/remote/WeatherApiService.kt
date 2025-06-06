package com.example.testvm.data.remote

import com.example.testvm.data.remote.response.ForecastResponse
import com.example.testvm.data.remote.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

    interface WeatherApiService {

        @GET("current.json")
        suspend fun getCurrentWeather(
            @Query("key") apiKey: String,
            @Query("q") query: String,
            @Query("aqi") aqi: String = "no"
        ): WeatherResponse

        @GET("forecast.json")
        suspend fun getForecastWeather(
            @Query("key") apiKey: String,
            @Query("q") query: String,
            @Query("days") days: Int = 7, //бесплатый токен только на 3дня
            @Query("aqi") aqi: String = "no"
        ): ForecastResponse
    }