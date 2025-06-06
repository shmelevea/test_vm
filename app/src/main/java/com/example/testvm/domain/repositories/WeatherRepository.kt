package com.example.testvm.domain.repositories

import com.example.testvm.data.remote.response.ForecastResponse
import com.example.testvm.data.remote.response.WeatherResponse


interface WeatherRepository {
    suspend fun getCurrentWeather(city: String): WeatherResponse
    suspend fun getForecastWeather(city: String): ForecastResponse
}