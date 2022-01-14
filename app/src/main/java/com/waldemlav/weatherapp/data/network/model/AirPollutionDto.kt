package com.waldemlav.weatherapp.data.network.model

import com.squareup.moshi.Json

data class AirPollutionDto(
    val coord: Coord,
    val list: List<Pollution>
) {
    data class Coord(
        val lon: Double,
        val lat: Double
    )

    data class Pollution(
        val main: Main,
        val components: Components,
        val dt: Int
    ) {
        data class Main(
            val aqi: Int
        )

        data class Components(
            val co: Double,
            val no: Double,
            val no2: Double,
            val o3: Double,
            val so2: Double,
            @Json(name = "pm2_5")
            val pm25: Double,
            val pm10: Double,
            val nh3: Double
        )
    }
}