package com.example.weatherapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET ("geo/1.0/direct?")
    suspend fun getCityCoordinates (
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("limit") limit: Int
    ): List<CityCoordinates>
    @GET("data/2.5/weather")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): WeatherResponse
    @GET("data/2.5/weather")
    suspend fun getForecastData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): ForecastResponse

    companion object{
        private const val BASE_URL = "https://api.openweathermap.org/"

        fun create(): WeatherApi{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(WeatherApi::class.java)
        }
    }
}

data class CityCoordinates(
    val name: String,
    val local_names: Map<String, String>,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)

data class WeatherResponse(
    val dt: Long,          // Add this field to store Unix timestamp
    val dt_txt: String,    // Add this field to store readable date string
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,    // Add other related fields if needed
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: Sys,
    val name: String
)

data class ForecastResponse(
    val dt: Long,          // Add this field to store Unix timestamp
    val dt_txt: String,    // Add this field to store readable date string
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,    // Add other related fields if needed
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: Sys
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Main(
    val temp: Float,
    val humidity: Int,
    val temp_min: Float,
    val temp_max: Float,  // Add other relevant fields
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val feels_like: Float,
    val temp_kf: Float
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Clouds(
    val all: Int
)

data class Wind(
    val speed: Float,
    val deg: Int,
    val gust: Float
)

data class Sys(
    val pod: String
)
