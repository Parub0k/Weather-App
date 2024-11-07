package com.example.weatherapp

data class CitiesResponse(
    val data: List<City>
)

data class City(
    val name: String,
    val country: String
)