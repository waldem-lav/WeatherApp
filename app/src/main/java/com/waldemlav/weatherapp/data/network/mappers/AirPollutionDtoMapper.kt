package com.waldemlav.weatherapp.data.network.mappers

import com.waldemlav.weatherapp.data.cache.model.AirPollution
import com.waldemlav.weatherapp.data.network.model.AirPollutionDto
import javax.inject.Inject

class AirPollutionDtoMapper
@Inject
constructor(){

    fun mapToCacheModel(dtoModel: AirPollutionDto): AirPollution {
        return AirPollution(
            aqi = dtoModel.list[0].main.aqi,
            dt = dtoModel.list[0].dt,
            pm25 = dtoModel.list[0].components.pm25,
            pm10 = dtoModel.list[0].components.pm10,
            so2 = dtoModel.list[0].components.so2,
            no2 = dtoModel.list[0].components.no2,
            o3 = dtoModel.list[0].components.o3,
            co = dtoModel.list[0].components.co
        )
    }
}