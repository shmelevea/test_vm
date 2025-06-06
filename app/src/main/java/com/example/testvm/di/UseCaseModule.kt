package com.example.testvm.di

import com.example.testvm.domain.repositories.WeatherRepository
import com.example.testvm.domain.usecase.GetCurrentWeatherUseCase
import com.example.testvm.domain.usecase.GetForecastWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCurrentWeatherUseCase(repository: WeatherRepository): GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(repository)
    }

    @Provides
    fun provideGetForecastWeatherUseCase(repository: WeatherRepository): GetForecastWeatherUseCase {
        return GetForecastWeatherUseCase(repository)
    }
}