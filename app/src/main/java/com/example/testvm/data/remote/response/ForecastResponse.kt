package com.example.testvm.data.remote.response

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    val location: Location,
    val forecast: Forecast
)

data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val day: Day,
    val astro: Astro,
    val hour: List<Hour>
)

data class Day(
    @SerializedName("maxtemp_c")
    val maxtemp_c: Double,
    @SerializedName("mintemp_c")
    val mintempC: Double,
    @SerializedName("avgtemp_c")
    val avgtempC: Double,
    val condition: Condition
)

data class Astro(
    val sunrise: String,
    val sunset: String
)

data class Hour(
    val time: String,
    @SerializedName("temp_c")
    val tempC: Double,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)