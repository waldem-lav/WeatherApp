package com.waldemlav.weatherapp.data.network.mappers

import com.waldemlav.weatherapp.data.cache.model.CurrentWeather
import com.waldemlav.weatherapp.data.cache.model.DailyWeather
import com.waldemlav.weatherapp.data.network.model.WeatherDto
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherDtoMapper
@Inject
constructor() {

    fun mapWeatherToCacheModel(dtoModel: WeatherDto): CurrentWeather {
        return CurrentWeather(
            currentTemp = dtoModel.current.temp?.roundToInt(),
            weatherDescription = dtoModel.current.weather[0].description,
        )
    }

    fun mapDailyWeatherToCacheModel(dtoModel: WeatherDto): List<DailyWeather> {
        return dtoModel.daily.map { mapDaily(it) }
    }

    private fun mapDaily(dailyWeather: WeatherDto.Daily): DailyWeather {
        return DailyWeather(
            tempMin = dailyWeather.temp.min?.roundToInt(),
            tempMax = dailyWeather.temp.max?.roundToInt(),
            mainDescription = dailyWeather.weather[0].main
        )
    }
}