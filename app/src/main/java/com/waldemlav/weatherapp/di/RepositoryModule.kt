package com.waldemlav.weatherapp.di

import com.waldemlav.weatherapp.data.cache.database.CityDao
import com.waldemlav.weatherapp.data.cache.database.LocalDao
import com.waldemlav.weatherapp.data.cache.mappers.WeatherMapper
import com.waldemlav.weatherapp.data.network.AirPollutionRemoteDataSource
import com.waldemlav.weatherapp.data.network.mappers.WeatherDtoMapper
import com.waldemlav.weatherapp.data.network.WeatherRemoteDataSource
import com.waldemlav.weatherapp.data.network.mappers.AirPollutionDtoMapper
import com.waldemlav.weatherapp.data.repository.MainRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    fun provideMainRepository(
        weatherRemoteDataSource: WeatherRemoteDataSource,
        airPollutionRemoteDataSource: AirPollutionRemoteDataSource,
        cityDao: CityDao,
        localDao: LocalDao,
        weatherDtoMapper: WeatherDtoMapper,
        airPollutionDtoMapper: AirPollutionDtoMapper,
        weatherMapper: WeatherMapper
    ): MainRepository {
        return MainRepository(
            weatherRemoteDataSource,
            airPollutionRemoteDataSource,
            cityDao,
            localDao,
            weatherDtoMapper,
            airPollutionDtoMapper,
            weatherMapper
        )
    }
}