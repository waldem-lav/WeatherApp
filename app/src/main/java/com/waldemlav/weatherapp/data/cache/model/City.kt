package com.waldemlav.weatherapp.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="city")
data class City(
    @PrimaryKey val id: Int,
    val name: String,
    val state: String,
    val country: String,
    val lon: Double,
    val lat: Double
)