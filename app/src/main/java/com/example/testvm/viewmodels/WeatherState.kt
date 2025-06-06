package com.example.testvm.viewmodels

import com.example.testvm.data.remote.response.ForecastResponse
import com.example.testvm.data.remote.response.WeatherResponse


data class WeatherState(
    val isLoading: Boolean = false,
    val currentWeather: WeatherResponse? = null,
    val forecastWeather: ForecastResponse? = null,
    val error: String? = null
)