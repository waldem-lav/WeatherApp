package com.waldemlav.weatherapp.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "air_pollution")
data class AirPollution(
    val aqi: Int,
    val dt: Int,
    @ColumnInfo(name = "pm2_5")
    val pm25: Double,
    val pm10: Double,
    val so2: Double,
    val no2: Double,
    val o3: Double,
    val co: Double
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}