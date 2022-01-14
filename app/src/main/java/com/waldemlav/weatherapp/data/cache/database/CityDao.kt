package com.waldemlav.weatherapp.data.cache.database

import androidx.room.*
import com.waldemlav.weatherapp.data.cache.model.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM city WHERE name LIKE :cityName || '%' ORDER BY name")
    fun getCitiesByName(cityName: String): Flow<List<City>>
}