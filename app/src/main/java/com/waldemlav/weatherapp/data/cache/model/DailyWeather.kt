package com.waldemlav.weatherapp.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_weather")
data class DailyWeather(
    @ColumnInfo(name = "temp_min")
    val tempMin: Int?,
    @ColumnInfo(name = "temp_max")
    val tempMax: Int?,
    @ColumnInfo(name = "main_description")
    val mainDescription: String?
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
