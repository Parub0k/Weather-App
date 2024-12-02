package com.example.weatherapp

import android.graphics.Region
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//   ViewModel для управління запитами до GeoDb API та зберіганням стану отриманих даних.
//   Здійснює пошук міст та надає доступ до результатів через StateFlow.

open class WeatherViewModel: ViewModel() {

    // Інстанси для WeatherApi
    val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData
    private val weatherApi = WeatherApi.create()

    // Інстанси для GeoDbApi
    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities
    private val geoDbApi = GeoDbApi.create()

//     Виконує запит на отримання списку міст із введеною частиною назви.
//     Оновлює _cities при успішному отриманні даних.
//     @param cityName частина назви міста для пошуку

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

//     Запит на отримання даних про погоду (для майбутнього використання).
//     @param city назва міста
//     @param apiKey ключ API для сервісу погоди

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