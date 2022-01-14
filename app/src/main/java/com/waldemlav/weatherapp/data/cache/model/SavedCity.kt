package com.waldemlav.weatherapp.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="saved_city")
data class SavedCity(
    @PrimaryKey val id: Int,
    val name: String,
    val state: String,
    val country: String,
    val lon: Double,
    val lat: Double,
    @ColumnInfo(name = "current_temp")
    var currentTemp: Double?,
    @ColumnInfo(name = "min_temp")
    var minTemp: Double?,
    @ColumnInfo(name = "max_temp")
    var maxTemp: Double?,
    var aqi: Int?,
    @ColumnInfo(name = "is_current")
    var isCurrent: Boolean,
)