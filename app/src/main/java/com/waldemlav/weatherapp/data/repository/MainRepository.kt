package com.waldemlav.weatherapp.data.repository

import com.waldemlav.weatherapp.data.cache.database.CityDao
import com.waldemlav.weatherapp.data.cache.database.LocalDao
import com.waldemlav.weatherapp.data.cache.mappers.WeatherMapper
import com.waldemlav.weatherapp.data.network.mappers.WeatherDtoMapper
import com.waldemlav.weatherapp.data.network.mappers.AirPollutionDtoMapper
import com.waldemlav.weatherapp.data.cache.model.City
import com.waldemlav.weatherapp.data.cache.model.SavedCity
import com.waldemlav.weatherapp.data.network.AirPollutionRemoteDataSource
import com.waldemlav.weatherapp.data.network.WeatherRemoteDataSource
import com.waldemlav.weatherapp.data.network.model.AirPollutionDto
import com.waldemlav.weatherapp.data.network.model.WeatherDto
import com.waldemlav.weatherapp.domain.model.CurrentCity
import com.waldemlav.weatherapp.domain.model.Weather
import com.waldemlav.weatherapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val airPollutionRemoteDataSource: AirPollutionRemoteDataSource,
    private val cityDao: CityDao,
    private val localDao: LocalDao,
    private val weatherDtoMapper: WeatherDtoMapper,
    private val airPollutionDtoMapper: AirPollutionDtoMapper,
    private val weatherMapper: WeatherMapper
) {

    fun getCitiesByName(cityName: String): Flow<List<City>> {
        return cityDao.getCitiesByName(cityName)
    }

    fun getSavedCitiesAsFlow(): Flow<List<SavedCity>> {
        return localDao.getSavedCitiesAsFlow()
    }

    suspend fun getCurrentSavedCity(): CurrentCity? {
        return withContext(Dispatchers.IO) {
            val savedCity = localDao.getCurrentSavedCity()
            if (savedCity != null) {
                CurrentCity(
                    name = savedCity.name, lon = savedCity.lon.toString(),
                    lat = savedCity.lat.toString()
                )
            } else {
                null
            }
        }
    }

    suspend fun getSavedCitiesCount(): Int {
        return withContext(Dispatchers.IO) {
            localDao.getSavedCitiesCount()
        }
    }

    suspend fun resetCurrentSavedCity() {
        withContext(Dispatchers.IO) {
            localDao.resetCurrentSavedCity()
        }
    }

    suspend fun insertSavedCity(savedCity: SavedCity) {
        withContext(Dispatchers.IO) {
            localDao.insertSavedCity(savedCity)
        }
    }

    suspend fun updateSavedCity(savedCity: SavedCity) {
        withContext(Dispatchers.IO) {
            localDao.updateSavedCity(savedCity)
        }
    }

    suspend fun deleteSavedCity(savedCity: SavedCity) {
        withContext(Dispatchers.IO) {
            localDao.deleteSavedCity(savedCity)
        }
    }

    suspend fun getWeather(): Weather? {
        return withContext(Dispatchers.IO) {
            val currentWeather = localDao.getCurrentWeather()
            val dailyWeatherList = localDao.getDailyWeatherList()
            val airPollution = localDao.getAirPollution()
            if (currentWeather != null && dailyWeatherList != null && airPollution != null) {
                weatherMapper.mapToDomainModel(currentWeather, dailyWeatherList, airPollution)
            } else
                null
        }
    }

    suspend fun fetchWeather(lat: String, lon: String, apiKey: String): Resource<WeatherDto> {
        return try {
            val response = weatherRemoteDataSource.getWeatherData(lat, lon, apiKey)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun fetchAirPollution(
        lat: String,
        lon: String,
        apiKey: String
    ): Resource<AirPollutionDto> {
        return try {
            val response = airPollutionRemoteDataSource.getAirPollutionData(lat, lon, apiKey)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun cacheWeather(weather: WeatherDto, airPollution: AirPollutionDto) {
        withContext(Dispatchers.IO) {
            deleteCachedData()
            localDao.insertCurrentWeather(weatherDtoMapper.mapWeatherToCacheModel(weather))
            localDao.insertDailyWeatherList(weatherDtoMapper.mapDailyWeatherToCacheModel(weather))
            localDao.insertAirPollution(airPollutionDtoMapper.mapToCacheModel(airPollution))
        }
    }

    suspend fun deleteCachedData() {
        withContext(Dispatchers.IO) {
            localDao.deleteCurrentWeather()
            localDao.deleteDailyWeather()
            localDao.deleteAirPollution()
        }
    }

    suspend fun isCityAlreadySaved(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            localDao.getSavedCitiesCountById(id) == 1
        }
    }

    suspend fun isSavedCityCurrent(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            localDao.getCurrentSavedCitiesCountById(id) == 1
        }
    }

    suspend fun getSavedCities(): List<SavedCity> {
        return withContext(Dispatchers.IO) {
            localDao.getSavedCities()
        }
    }

    suspend fun updateSavedCityWeatherData(savedCity: SavedCity, apiKey: String) {
        withContext(Dispatchers.IO) {
            val weather = fetchWeather(
                lon = savedCity.lon.toString(),
                lat = savedCity.lat.toString(), apiKey = apiKey
            )
            val pollution = fetchAirPollution(
                lon = savedCity.lon.toString(),
                lat = savedCity.lat.toString(), apiKey = apiKey
            )
            if (weather.data != null && pollution.data != null) {
                savedCity.currentTemp = weather.data.current.temp
                savedCity.minTemp = weather.data.daily[0].temp.min
                savedCity.maxTemp = weather.data.daily[0].temp.max
                savedCity.aqi = pollution.data.list[0].main.aqi
                updateSavedCity(savedCity)
            }
        }
    }
}