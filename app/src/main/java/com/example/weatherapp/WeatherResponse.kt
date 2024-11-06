package com.example.weatherapp

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>
) {
    fun isNotEmpty(): Boolean {
        return name.isNotEmpty() && main != null && weather.isNotEmpty()
    }
}

data class Main (
    val temp: Float,
    val humidity: Int
)

data class Weather (
    val description: String
)