package com.waldemlav.weatherapp.data.network

import com.waldemlav.weatherapp.data.network.model.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherApi: WeatherApi
) {
    suspend fun getWeatherData(lat: String, lon: String, apiKey: String): Response<WeatherDto> {
        return weatherApi.getWeatherData(lat, lon, apiKey)
    }
}

interface WeatherApi {
    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "minutely,hourly,alerts"
    ): Response<WeatherDto>
}