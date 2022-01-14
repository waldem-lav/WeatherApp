package com.waldemlav.weatherapp.domain.model

data class Weather(
    var currentTemp: String,
    var weatherDescription: String,
    val daily: List<Daily>,
    val aqi: Aqi
) {
    data class Daily(
        val tempMin: String,
        val tempMax: String,
        val mainDescription: String,
        val dayOfWeek: String,
        val date: String,
    )

    data class Aqi(
        val main: Int,
        val dt: Int,
        val pm25: String,
        val pm10: String,
        val so2: String,
        val no2: String,
        val o3: String,
        val co: String
    )
}