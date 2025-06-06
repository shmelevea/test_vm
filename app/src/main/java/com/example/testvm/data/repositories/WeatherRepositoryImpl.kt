package com.example.testvm.data.repositories

import com.example.testvm.data.remote.WeatherApiService
import com.example.testvm.data.remote.response.ForecastResponse
import com.example.testvm.data.remote.response.WeatherResponse
import com.example.testvm.domain.repositories.WeatherRepository

class WeatherRepositoryImpl(private val api: WeatherApiService) : WeatherRepository {

    override suspend fun getCurrentWeather(city: String): WeatherResponse {
        return api.getCurrentWeather(API_KEY, city)
    }

    override suspend fun getForecastWeather(city: String): ForecastResponse {
        return api.getForecastWeather(API_KEY, city)
    }

    companion object {
        const val API_KEY = "15c666f5f9ef4f85be2143159240104"
    }
}