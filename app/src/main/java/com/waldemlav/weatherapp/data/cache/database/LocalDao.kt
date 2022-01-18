package com.waldemlav.weatherapp.data.cache.database

import androidx.room.*
import com.waldemlav.weatherapp.data.cache.model.AirPollution
import com.waldemlav.weatherapp.data.cache.model.CurrentWeather
import com.waldemlav.weatherapp.data.cache.model.DailyWeather
import com.waldemlav.weatherapp.data.cache.model.SavedCity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    @Query("SELECT * FROM saved_city ORDER BY is_current DESC, name")
    fun getSavedCitiesAsFlow(): Flow<List<SavedCity>>

    @Query("SELECT * FROM saved_city")
    fun getSavedCities(): List<SavedCity>

    @Query("SELECT * FROM saved_city WHERE is_current = 1")
    suspend fun getCurrentSavedCity(): SavedCity?

    @Query("SELECT COUNT(*) FROM saved_city")
    suspend fun getSavedCitiesCount(): Int

    @Query("SELECT COUNT(*) FROM saved_city WHERE id=:id")
    suspend fun getSavedCitiesCountById(id: Int): Int

    @Query("SELECT COUNT(*) FROM saved_city WHERE id=:id AND is_current = 1")
    suspend fun getCurrentSavedCitiesCountById(id: Int): Int

    @Insert
    suspend fun insertSavedCity(savedCity: SavedCity)

    @Query("UPDATE saved_city SET is_current = 0 WHERE is_current = 1")
    suspend fun resetCurrentSavedCity()

    @Update
    suspend fun updateSavedCity(savedCity: SavedCity)

    @Delete
    suspend fun deleteSavedCity(savedCity: SavedCity)

    @Query("SELECT * FROM current_weather")
    suspend fun getCurrentWeather(): CurrentWeather?

    @Query("SELECT * FROM daily_weather")
    suspend fun getDailyWeatherList(): List<DailyWeather>?

    @Query("DELETE FROM current_weather")
    suspend fun deleteCurrentWeather()

    @Query("DELETE FROM daily_weather")
    suspend fun deleteDailyWeather()

    @Insert
    suspend fun insertCurrentWeather(currentWeather: CurrentWeather)

    @Insert
    suspend fun insertDailyWeatherList(dailyWeatherList: List<DailyWeather>)

    @Query("SELECT * FROM air_pollution")
    suspend fun getAirPollution(): AirPollution?

    @Insert
    suspend fun insertAirPollution(airPollution: AirPollution)

    @Query("DELETE FROM air_pollution")
    suspend fun deleteAirPollution()
}