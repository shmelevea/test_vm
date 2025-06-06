package com.example.testvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testvm.data.remote.response.ForecastResponse
import com.example.testvm.data.remote.response.WeatherResponse
import com.example.testvm.domain.usecase.GetCurrentWeatherUseCase
import com.example.testvm.domain.usecase.GetForecastWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastWeatherUseCase: GetForecastWeatherUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState(isLoading = true))
    val state: StateFlow<WeatherState> = _state

    internal var currentCoordinates: String = ""

    init {
        startAutoUpdate()
    }

    private fun startAutoUpdate() {
        viewModelScope.launch {
            while (true) {
                if (currentCoordinates.isNotBlank()) {
                    loadWeather(currentCoordinates)
                }
                delay(60_000)
            }
        }
    }

    fun updateLocation(latitude: Double, longitude: Double) {
        currentCoordinates = "$latitude,$longitude"
        loadWeather(currentCoordinates)
    }

    fun loadWeather(city: String) {
        viewModelScope.launch {
            _state.value = WeatherState(isLoading = true)
            try {
                val current = getCurrentWeatherUseCase(city)
                val forecast = getForecastWeatherUseCase(city)

                _state.value = WeatherState(
                    isLoading = false,
                    currentWeather = current,
                    forecastWeather = forecast,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = WeatherState(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}
