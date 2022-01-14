package com.waldemlav.weatherapp.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.waldemlav.weatherapp.data.network.AirPollutionApi
import com.waldemlav.weatherapp.data.network.AirPollutionRemoteDataSource
import com.waldemlav.weatherapp.data.network.WeatherApi
import com.waldemlav.weatherapp.data.network.WeatherRemoteDataSource
import com.waldemlav.weatherapp.data.network.mappers.WeatherDtoMapper
import com.waldemlav.weatherapp.data.network.mappers.AirPollutionDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://api.openweathermap.org/data/2.5/")
    }

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit.Builder): WeatherApi {
        return retrofit.build().create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAirPollutionApi(retrofit: Retrofit.Builder): AirPollutionApi {
        return retrofit.build().create(AirPollutionApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRemoteDataSource(weatherApi: WeatherApi): WeatherRemoteDataSource {
        return WeatherRemoteDataSource(weatherApi)
    }

    @Singleton
    @Provides
    fun provideAirPollutionRemoteDataSource(
        airPollutionApi: AirPollutionApi
    ): AirPollutionRemoteDataSource {
        return AirPollutionRemoteDataSource(airPollutionApi)
    }

    @Singleton
    @Provides
    fun provideWeatherDtoMapper(): WeatherDtoMapper {
        return WeatherDtoMapper()
    }

    @Singleton
    @Provides
    fun provideAirPollutionDtoMapper(): AirPollutionDtoMapper {
        return AirPollutionDtoMapper()
    }
}