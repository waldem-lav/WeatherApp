package com.waldemlav.weatherapp.data.network

import com.waldemlav.weatherapp.data.network.model.AirPollutionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class AirPollutionRemoteDataSource @Inject constructor(
    private val airPollutionApi: AirPollutionApi
) {
    suspend fun getAirPollutionData(
        lat: String,
        lon: String,
        apiKey: String
    ): Response<AirPollutionDto> {
        return airPollutionApi.getAirPollutionData(lat, lon, apiKey)
    }
}

interface AirPollutionApi {
    @GET("air_pollution")
    suspend fun getAirPollutionData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String
    ): Response<AirPollutionDto>
}