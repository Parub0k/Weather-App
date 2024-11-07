package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData
    private val weatherApi = WeatherApi.create()

    // Апі для пошуку міст
    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities
    private val geoDbApi = GeoDbApi.create()

    fun findCities(cityName: String) {
        viewModelScope.launch {
            try {
                val response = geoDbApi.findCities(cityName)
                _cities.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchWeather(city: String, apiKey: String){
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(city, apiKey)
                _weatherData.value = response
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}