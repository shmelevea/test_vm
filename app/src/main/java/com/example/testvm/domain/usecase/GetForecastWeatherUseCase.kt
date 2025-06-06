package com.example.testvm.domain.usecase

import com.example.testvm.domain.repositories.WeatherRepository

class GetForecastWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String) = repository.getForecastWeather(city)
}