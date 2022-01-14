package com.waldemlav.weatherapp.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeather(
    @ColumnInfo(name = "current_temp")
    var currentTemp: Int?,
    @ColumnInfo(name = "weather_description")
    var weatherDescription: String?
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}