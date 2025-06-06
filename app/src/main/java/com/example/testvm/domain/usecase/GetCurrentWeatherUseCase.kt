package com.example.testvm.domain.usecase

import com.example.testvm.domain.repositories.WeatherRepository

class GetCurrentWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String) = repository.getCurrentWeather(city)
}