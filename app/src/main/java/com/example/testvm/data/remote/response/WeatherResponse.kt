package com.example.testvm.data.remote.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String
)

data class Current(
    @SerializedName("temp_c")
    val tempC: Double,
    val condition: Condition
)