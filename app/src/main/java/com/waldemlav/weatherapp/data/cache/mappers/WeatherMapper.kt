package com.waldemlav.weatherapp.data.cache.mappers

import com.waldemlav.weatherapp.data.cache.model.AirPollution
import com.waldemlav.weatherapp.data.cache.model.CurrentWeather
import com.waldemlav.weatherapp.data.cache.model.DailyWeather
import com.waldemlav.weatherapp.domain.model.Weather
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherMapper
@Inject
constructor() {

    fun mapToDomainModel(
        currentWeather: CurrentWeather,
        dailyWeatherList: List<DailyWeather>,
        airPollution: AirPollution
    ): Weather {
        val dayList = getDaysOfWeek()
        val dateList = getDates()
        val dailyList = mutableListOf<Weather.Daily>()
        for (index in 0..6) {
            dailyList.add(
                mapDailyToDomainModel(
                    dailyWeatherList[index],
                    dayList[index],
                    dateList[index]
                )
            )
        }

        return Weather(
            currentTemp = currentWeather.currentTemp.toString(),
            weatherDescription = currentWeather.weatherDescription.toString(),
            daily = dailyList,
            aqi = Weather.Aqi(
                main = airPollution.aqi,
                dt = airPollution.dt,
                pm25 = airPollution.pm25.toString(),
                pm10 = airPollution.pm10.toString(),
                so2 = airPollution.so2.toString(),
                no2 = airPollution.no2.toString(),
                o3 = airPollution.o3.toString(),
                co = airPollution.co.toString()
            )
        )
    }

    private fun mapDailyToDomainModel(
        dailyWeather: DailyWeather,
        dayOfWeek: String,
        date: String
    ): Weather.Daily {
        return Weather.Daily(
            tempMin = dailyWeather.tempMin.toString(),
            tempMax = dailyWeather.tempMax.toString(),
            mainDescription = dailyWeather.mainDescription.toString(),
            dayOfWeek = dayOfWeek,
            date = date
        )
    }

    private fun getDaysOfWeek(): List<String> {
        val daysOfWeek = mutableListOf("Today", "Tomorrow")
        val formatter = SimpleDateFormat("E", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 2)
        repeat(5) {
            daysOfWeek.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return daysOfWeek
    }

    private fun getDates(): List<String> {
        val dates = mutableListOf<String>()
        val formatter = SimpleDateFormat("MM/dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(7) {
            dates.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dates
    }
}