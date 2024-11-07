package com.example.weatherapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
interface GeoDbApi {
    @GET("geo/cities")
    suspend fun findCities(
        @Query("namePrefix") cityName: String,
        @Query("limit") limit: Int = 5,
    ): CitiesResponse

    companion object {
        private const val BASE_URL = "http://geodb-free-service.wirefreethought.com/v1/"

        fun create(): GeoDbApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GeoDbApi::class.java)
        }
    }
}