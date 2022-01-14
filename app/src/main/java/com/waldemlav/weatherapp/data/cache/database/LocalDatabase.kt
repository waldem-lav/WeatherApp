package com.waldemlav.weatherapp.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waldemlav.weatherapp.data.cache.model.*

/**
 * Database containing all saved data
 */
@Database(
    entities = [
        SavedCity::class,
        AirPollution::class,
        CurrentWeather::class,
        DailyWeather::class],
    version = 1
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun localDao(): LocalDao

    companion object {
        const val DATABASE_NAME = "local_db"
    }
}