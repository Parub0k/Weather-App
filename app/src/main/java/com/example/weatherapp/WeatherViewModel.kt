package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.gson.Gson
import retrofit2.HttpException

//   ViewModel для управління запитами до GeoDb API та зберіганням стану отриманих даних.
//   Здійснює пошук міст та надає доступ до результатів через StateFlow.



open class WeatherViewModel : ViewModel() {

    private val weatherApi = WeatherApi.create()
    private val _rawResponse = MutableStateFlow<String?>(null)
    val rawResponse: StateFlow<String?> get() = _rawResponse
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> get() = _weatherData
    private val _forecastData = MutableStateFlow<ForecastResponse?>(null)
    val forecastData: StateFlow<ForecastResponse?> get() = _forecastData
    val gson = Gson()

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

    fun fetchCityCoordinates(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                println("Making API call to fetch city coordinates for $city")
                val response = weatherApi.getCityCoordinates(city, "086ea00f450ae7bcc30164389f96de55", 5)
                println("Received response: $response")
                val rawJson = gson.toJson(response)
                println("Converted response to JSON: $rawJson")
                _rawResponse.value = rawJson
                println("Updated _rawResponse with: ${_rawResponse.value}")
                if (response.isNotEmpty()) {
                    fetchWeatherData(response[0].lat, response[0].lon, apiKey)
                    fetchForecastData(response[0].lat, response[0].lon, apiKey)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                val errorBody = e.response()?.errorBody()?.string()
                _rawResponse.value = errorBody
                println("HTTP error: $errorBody")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchWeatherData(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            try {
                println("Making API call to fetch weather data for coordinates ($lat, $lon)")
                val response = weatherApi.getWeatherData(lat, lon, apiKey, "imperial")
                println("Received weather data response: $response")
                _weatherData.value = response
            } catch (e: HttpException) {
                e.printStackTrace()
                val errorBody = e.response()?.errorBody()?.string()
                println("HTTP error: $errorBody")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun fetchForecastData(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            try {
                println("Making API call to fetch forecast data for coordinates ($lat, $lon)")
                val response = weatherApi.getForecastData(lat, lon, apiKey)
                println("Received forecast data response: $response")
                _forecastData.value = response
            } catch (e: HttpException) {
                e.printStackTrace()
                val errorBody = e.response()?.errorBody()?.string()
                println("HTTP error: $errorBody")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
