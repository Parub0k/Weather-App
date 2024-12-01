package com.example.weatherapp

// Інтерфейс для роботи з GeoDb API.
// Виконує запит на отримання списку міст на основі введеної назви (параметр `namePrefix`).

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
interface GeoDbApi {

//
//     Виконує запит до GeoDb API для отримання списку міст.
//     @param cityName назва міста або його частина для пошуку
//     @param limit максимальна кількість результатів для отримання
//     @return CitiesResponse об'єкт, що містить список міст
//

    @GET("geo/cities")
    suspend fun findCities(
        @Query("namePrefix") cityName: String,
        @Query("limit") limit: Int = 10,
    ): CitiesResponse

    companion object {
        private const val BASE_URL = "http://geodb-free-service.wirefreethought.com/v1/"

//        Створює та налаштовує інстанс GeoDbApi з використанням Retrofit.
//        @return GeoDbApi інтерфейс для виклику API

        fun create(): GeoDbApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GeoDbApi::class.java)
        }
    }
}