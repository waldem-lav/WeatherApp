package com.waldemlav.weatherapp.data.network.model

import com.squareup.moshi.Json

data class WeatherDto(
    val lat: Double?,
    val lon: Double?,
    val timezone: String?,
    @Json(name = "timezone_offset")
    val timezoneOffset: Int?,
    val current: Current,
    val daily: List<Daily>
) {
    data class Current(
        val dt: Int?,
        val sunrise: Int?,
        val sunset: Int?,
        val temp: Double?,
        @Json(name = "feels_like")
        val feelsLike: Double?,
        val pressure: Int?,
        val humidity: Int?,
        @Json(name = "dew_poInt?")
        val dewPoInt: Double?,
        val uvi: Double?,
        val clouds: Int?,
        val visibility: Int?,
        @Json(name = "wind_speed")
        val windSpeed: Double?,
        @Json(name = "wind_deg")
        val windDeg: Int?,
        @Json(name = "wind_gust")
        val windGust: Double?,
        val rain: Rain?,
        val snow: Snow?,
        val weather: List<Weather>
    ) {
        data class Rain(
            @Json(name = "1h")
            val oneH: Double?
        )

        data class Snow(
            @Json(name = "1h")
            val oneH: Double?
        )

        data class Weather(
            val id: Int?,
            val main: String?,
            val description: String?,
            val icon: String?
        )
    }

    data class Daily(
        val dt: Int?,
        val sunrise: Int?,
        val sunset: Int?,
        val moonrise: Int?,
        val moonset: Int?,
        @Json(name = "moon_phase")
        val moonPhase: Double?,
        val temp: Temp,
        @Json(name = "feels_like")
        val feelsLike: FeelsLike,
        val pressure: Int?,
        val humidity: Int?,
        @Json(name = "dew_poInt?")
        val dewPoInt: Double?,
        @Json(name = "wind_speed")
        val windSpeed: Double?,
        @Json(name = "wind_deg")
        val windDeg: Int?,
        @Json(name = "wind_gust")
        val windGust: Double?,
        val weather: List<Weather>,
        val clouds: Int?,
        val pop: Double?,
        val uvi: Double?,
        val rain: Double?,
        val snow: Double?
    ) {
        data class Temp(
            val day: Double?,
            val min: Double?,
            val max: Double?,
            val night: Double?,
            val eve: Double?,
            val morn: Double?
        )

        data class FeelsLike(
            val day: Double?,
            val night: Double?,
            val eve: Double?,
            val morn: Double?
        )

        data class Weather(
            val id: Int?,
            val main: String?,
            val description: String?,
            val icon: String?
        )
    }
}