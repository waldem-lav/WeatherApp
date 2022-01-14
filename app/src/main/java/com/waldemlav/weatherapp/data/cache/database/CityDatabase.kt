package com.waldemlav.weatherapp.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waldemlav.weatherapp.data.cache.model.City

/**
 * Database containing all cities provided by OpenWeatherMap API
 */
@Database(entities = [City::class], version = 1)
abstract class CityDatabase: RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        const val DATABASE_NAME = "cities_db"
    }
}