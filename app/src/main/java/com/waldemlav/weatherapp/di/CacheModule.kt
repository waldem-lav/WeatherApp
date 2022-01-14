package com.waldemlav.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.waldemlav.weatherapp.data.cache.database.CityDatabase
import com.waldemlav.weatherapp.data.cache.database.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.waldemlav.weatherapp.data.cache.database.LocalDao
import com.waldemlav.weatherapp.data.cache.database.LocalDatabase
import com.waldemlav.weatherapp.data.cache.mappers.WeatherMapper

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideCityDb(@ApplicationContext context: Context): CityDatabase {
        return Room.databaseBuilder(
            context,
            CityDatabase::class.java,
            CityDatabase.DATABASE_NAME
        )
            .createFromAsset("database/cities.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideCityDao(cityDatabase: CityDatabase): CityDao {
        return cityDatabase.cityDao()
    }

    @Singleton
    @Provides
    fun provideLocalDb(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            LocalDatabase.DATABASE_NAME
        )
            .createFromAsset("database/local.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideLocalDao(localDatabase: LocalDatabase): LocalDao {
        return localDatabase.localDao()
    }

    @Singleton
    @Provides
    fun provideWeatherMapper(): WeatherMapper {
        return WeatherMapper()
    }
}