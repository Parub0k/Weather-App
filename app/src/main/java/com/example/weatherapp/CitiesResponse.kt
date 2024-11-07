package com.example.weatherapp


//    Відповідь від GeoDb API, що містить список міст у вигляді об'єктів City.
//    @property data список міст

data class CitiesResponse(
    val data: List<City>
)

data class City(
    val name: String,
    val country: String
)